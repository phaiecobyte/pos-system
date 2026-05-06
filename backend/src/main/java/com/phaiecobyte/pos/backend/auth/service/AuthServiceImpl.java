package com.phaiecobyte.pos.backend.auth.service;

import com.phaiecobyte.pos.backend.auth.dto.AuthRequest;
import com.phaiecobyte.pos.backend.auth.dto.AuthResponse;
import com.phaiecobyte.pos.backend.auth.dto.RefreshTokenRequest;
import com.phaiecobyte.pos.backend.auth.dto.RegisterRequest;
import com.phaiecobyte.pos.backend.auth.entity.InvalidatedToken;
import com.phaiecobyte.pos.backend.auth.entity.RefreshToken;
import com.phaiecobyte.pos.backend.auth.entity.Role;
import com.phaiecobyte.pos.backend.auth.entity.User;
import com.phaiecobyte.pos.backend.auth.repository.InvalidatedTokenRepository;
import com.phaiecobyte.pos.backend.auth.repository.RefreshTokenRepository;
import com.phaiecobyte.pos.backend.auth.repository.RoleRepository;
import com.phaiecobyte.pos.backend.auth.repository.UserRepository;
import com.phaiecobyte.pos.backend.auth.security.JwtService;
import com.phaiecobyte.pos.backend.core.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final InvalidatedTokenRepository invalidatedTokenRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Value("${app.jwt-refresh-expiration-ms}")
    private long refreshExpirationMs;

    @Override
    public AuthResponse authenticate(AuthRequest request) {

        try {
            //1 ផ្ទៀងផ្ទាត់ Username និង Password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            //2. ទាញយកអ្នកប្រើប្រាស់
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(()-> new AppException(HttpStatus.NOT_FOUND, "User not found!"));

            //3. បង្កើត accessToken
            String accessToken = jwtService.generateToken(user);

            //4. បង្កើត ឬកែប្រែ Refresh Token ក្នុង Database
            String refreshTokenStr = jwtService.generateRefreshToken();
            saveRefreshToken(user, refreshTokenStr);

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshTokenStr)
                    .build();

        }catch (BadCredentialsException e){
            // ចាប់កំហុសនៅពេលវាយលេខសម្ងាត់ខុស ហើយបោះសារត្រឡប់ទៅ Frontend ឱ្យបានត្រឹមត្រូវ
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
    public AuthResponse register(RegisterRequest request) {
        // ១. ពិនិត្យមើលថាតើ Username នេះមានអ្នកប្រើហើយឬនៅ?
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Username is existed!");
        }

        // ២. ពិនិត្យមើលថាតើ Email នេះមានអ្នកប្រើហើយឬនៅ?
        if (request.getEmail() != null && userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Email is already in use!");
        }

        // ៣. រកមើល Role នៅក្នុង Database
        String assignRole = (request.getRoleName() != null && !request.getRoleName().isEmpty())
                ? request.getRoleName() : "CASHIER";

        Role userRole = roleRepository.findByName(assignRole)
                .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, "Role not found!: " + assignRole));

        // ៤. បង្កើត Entity User ថ្មី
        User user = User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(userRole))
                .active(true)
                .build();

        // ៥. Save ចូល Database
        userRepository.save(user);

        return new AuthResponse(null,null);
    }

    @Override
    @Transactional
    public void logout(User user, HttpServletRequest request) {
        // ១. លុប Refresh Token ចេញពី Database ដូចមុន
        refreshTokenRepository.deleteByUser(user);

        // ២. ទាញយក Access Token ពី Request Header
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.toLowerCase().startsWith("bearer ")) {
            String jwt = authHeader.substring(7);

            try {
                // ៣. ទាញយកថ្ងៃផុតកំណត់ (Expiration Date) ពី Token នោះ
                Date expirationDate = jwtService.extractExpiration(jwt); // អ្នកត្រូវបង្កើត method នេះក្នុង JwtService បើមិនទាន់មាន

                // ៤. បញ្ចូល Token នោះទៅក្នុង Blacklist (InvalidatedToken)
                InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                        .id(jwt)
                        .expiryTime(expirationDate)
                        .build();

                invalidatedTokenRepository.save(invalidatedToken);
            } catch (Exception e) {
                // បើ Token ហ្នឹងខូចស្រាប់ ឬផុតកំណត់ស្រាប់ហើយ យើងមិនបាច់ធ្វើអីទេ គ្រាន់តែ Ignore វាទៅ
            }
        }
    }

    // បន្ថែមចូលក្នុង AuthServiceImpl.java
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        return refreshTokenRepository.findByToken(request.getRefreshToken())
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    // ផ្លាស់ប្តូរត្រង់នេះ: កុំ return ResponseEntity ក្នុង Service
                    return new AuthResponse(accessToken, request.getRefreshToken());
                })
                .orElseThrow(() -> new AppException(HttpStatus.FORBIDDEN, "Refresh token is invalid or expired!"));
    }

    private RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            // បើផុតកំណត់ហើយ លុបវាចេញពី Database
            refreshTokenRepository.delete(token);
            throw new AppException(HttpStatus.FORBIDDEN, "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }


}