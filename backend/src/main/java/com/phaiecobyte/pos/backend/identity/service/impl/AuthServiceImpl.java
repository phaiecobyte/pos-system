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
import com.phaiecobyte.pos.backend.identity.security.SecurityUser;
import com.phaiecobyte.pos.backend.identity.security.CustomAuthenticationToken;
import com.phaiecobyte.pos.backend.identity.service.AuthService;
import com.phaiecobyte.pos.backend.identity.service.LoginAttemptService;
import com.phaiecobyte.pos.backend.tenant.repository.TenantRepository;
import com.phaiecobyte.pos.backend.tenant.api.TenantLookup;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final InvalidatedTokenRepository invalidatedTokenRepository;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TenantLookup tenantLookup;
//    private final TenantRepository tenantRepository;
    private final LoginAttemptService loginAttemptService;

    @Value("${app.jwt-refresh-expiration-ms}")
    private long refreshExpirationMs;

    @Override
    public TokenPair authenticate(AuthRequest request) {

        try {

            var tenant = tenantLookup.findByCode(request.getTenantCode())
                            .orElseThrow(()-> new AppException(HttpStatus.NOT_FOUND,"Tenant is not found"));

            // Check if account is locked due to brute-force attempts
            if (loginAttemptService.isAccountLocked(request.getUsername(), tenant.id())) {
                throw new AppException(HttpStatus.LOCKED, 
                    "Account is temporarily locked due to multiple failed login attempts. Please try again later.");
            }

//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//            );
            authenticationManager.authenticate(
                    new CustomAuthenticationToken(
                            request.getTenantCode(),
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            User user = userRepository.findByUsernameAndTenantId(
                            request.getUsername(),
                            tenant.id()
                    )
                    .orElseThrow(() ->
                            new AppException(
                                    HttpStatus.NOT_FOUND,
                                    "User not found!"
                            )
                    );

            SecurityUser securityUser =
                    new SecurityUser(user);

            String accessToken =
                    jwtService.generateToken(
                            securityUser,
                            user.getTenantId(),
                            tenant.code()
                    );

            String refreshTokenStr = jwtService.generateRefreshToken();
            saveRefreshToken(user, refreshTokenStr);

            // Record successful authentication attempt
            loginAttemptService.recordSuccessfulAttempt(request.getUsername(), tenant.id());

            return new TokenPair(accessToken,refreshTokenStr);

        }catch (BadCredentialsException e){
            // Extract tenant for logging failed attempt
            try {
                var tenant = tenantLookup.findByCode(request.getTenantCode()).orElse(null);
                if (tenant != null) {
                    loginAttemptService.recordFailedAttempt(request.getUsername(), tenant.id());
                }
            } catch (Exception ex) {
                log.debug("Could not record failed login attempt: {}", ex.getMessage());
            }
            throw new AppException(HttpStatus.UNAUTHORIZED, "Invalid Username or Password!");
        }
    }

    private void saveRefreshToken(User user, String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .orElse(new RefreshToken());

        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(LocalDateTime.now().plus(Duration.ofMillis(refreshExpirationMs)));
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
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
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

        SecurityUser securityUser = new SecurityUser(user);

        // Get tenant info to include in new token - preserve tenant context
        var tenant = tenantLookup.findById(user.getTenantId())
                .orElseThrow(() -> new AppException(
                        HttpStatus.NOT_FOUND,
                        "Tenant not found"
                ));

        String accessToken = jwtService.generateToken(
                securityUser,
                user.getTenantId(),
                tenant.code()
        );

        String newRefreshToken =
                jwtService.generateRefreshToken();

        saveRefreshToken(user, newRefreshToken);

        return new TokenPair(
                accessToken,
                newRefreshToken
        );
    }


}