package com.phaiecobyte.pos.backend.identity.service.impl;

import com.phaiecobyte.pos.backend.core.common.exception.AppException;
import com.phaiecobyte.pos.backend.identity.dto.AuthRequest;
import com.phaiecobyte.pos.backend.identity.dto.TokenPair;
import com.phaiecobyte.pos.backend.identity.model.InvalidatedToken;
import com.phaiecobyte.pos.backend.identity.model.RefreshToken;
import com.phaiecobyte.pos.backend.identity.model.User;
import com.phaiecobyte.pos.backend.identity.repository.InvalidatedTokenRepository;
import com.phaiecobyte.pos.backend.identity.repository.RefreshTokenRepository;
import com.phaiecobyte.pos.backend.identity.repository.UserRepository;
import com.phaiecobyte.pos.backend.identity.security.JwtService;
import com.phaiecobyte.pos.backend.identity.service.AuthService;
import com.phaiecobyte.pos.backend.tenant.api.TenantLookup;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final InvalidatedTokenRepository invalidatedTokenRepository;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TenantLookup tenantLookup;

    @Value("${app.jwt-refresh-expiration-ms}")
    private long refreshExpirationMs;

    @Override
    public TokenPair authenticate(AuthRequest request) {

        try {

            var tenant = tenantLookup.findByCode(request.getTenantCode())
                            .orElseThrow(()-> new AppException(HttpStatus.NOT_FOUND,"Tenant is not found"));

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            User user = userRepository.findByUsernameAndTenantId(
                            request.getUsername(),
                            tenant.id()
                    )
                    .orElseThrow(()-> new AppException(HttpStatus.NOT_FOUND, "User not found!"));

            String accessToken = jwtService.generateToken(user, tenant.code());

            String refreshTokenStr = jwtService.generateRefreshToken();
            saveRefreshToken(user, refreshTokenStr);

            return new TokenPair(accessToken,refreshTokenStr);

        }catch (BadCredentialsException e){
            throw new AppException(HttpStatus.UNAUTHORIZED, "Invalid Username or Password!");
        }
    }

    private void saveRefreshToken(User user, String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .orElse(new RefreshToken());

        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpirationMs));
        refreshTokenRepository.save(refreshToken);
    }


    @Override
    @Transactional
    public void logout(User user, HttpServletRequest request) {
        refreshTokenRepository.deleteByUser(user);

        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.toLowerCase().startsWith("bearer ")) {
            String jwt = authHeader.substring(7);

            try {

                Date expirationDate = jwtService.extractExpiration(jwt);

                InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                        .id(jwt)
                        .expiryTime(expirationDate)
                        .build();

                invalidatedTokenRepository.save(invalidatedToken);
            } catch (Exception e) {
               log.error("Logout error:{}",e.getMessage());
            }
        }
    }


    private RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new AppException(HttpStatus.FORBIDDEN, "Refresh token was expired. Please make a new sign in request");
        }
        return token;
    }

    @Transactional
    @Override
    public TokenPair refreshToken(String refreshToken) {

        RefreshToken token = refreshTokenRepository
                .findByToken(refreshToken)
                .map(this::verifyExpiration)
                .orElseThrow(() -> new AppException(
                        HttpStatus.FORBIDDEN,
                        "Refresh token is invalid or expired!"
                ));

        User user = token.getUser();

        String accessToken = jwtService.generateToken(user);

        String newRefreshToken =
                jwtService.generateRefreshToken();

        saveRefreshToken(user, newRefreshToken);

        return new TokenPair(
                accessToken,
                newRefreshToken
        );
    }


}