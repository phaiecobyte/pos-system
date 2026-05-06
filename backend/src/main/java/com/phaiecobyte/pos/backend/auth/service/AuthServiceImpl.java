package com.phaiecobyte.pos.backend.auth.service;

import com.phaiecobyte.pos.backend.auth.dto.AuthRequest;
import com.phaiecobyte.pos.backend.auth.dto.AuthResponse;
import com.phaiecobyte.pos.backend.auth.dto.RefreshTokenRequest;
import com.phaiecobyte.pos.backend.auth.dto.RegisterRequest;
import com.phaiecobyte.pos.backend.auth.entity.RefreshToken;
import com.phaiecobyte.pos.backend.auth.entity.Role;
import com.phaiecobyte.pos.backend.auth.entity.User;
import com.phaiecobyte.pos.backend.auth.repository.RefreshTokenRepository;
import com.phaiecobyte.pos.backend.auth.repository.RoleRepository;
import com.phaiecobyte.pos.backend.auth.repository.UserRepository;
import com.phaiecobyte.pos.backend.auth.security.JwtService;
import com.phaiecobyte.pos.backend.core.exception.AppException;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

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

        // ២. រកមើល Role នៅក្នុង Database
        String assignRole = (request.getRoleName() != null && !request.getRoleName().isEmpty())
                ? request.getRoleName() : "CASHIER";

        Role userRole = roleRepository.findByName(assignRole)
                .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, "Role not found!: " + assignRole));

        // ៣. បង្កើត Entity User ថ្មី
        var user = new User();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // ៤. កំណត់ Role ជា Set ជំនួសឱ្យ Object តែមួយ
        user.setRoles(Set.of(userRole)); // <--- ប្រើ Set.of() ដើម្បីខ្ចប់ Role ចូលក្នុង Set

        user.setActive(true);

        // ៥. Save ចូល Database
        userRepository.save(user);

        return new AuthResponse(null,null);
    }

    @Override
    @Transactional
    public void logout(User user) {
        //លុប Refresh Token របស់់ User នេះចេញពី Database
        refreshTokenRepository.deleteByUser(user);
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