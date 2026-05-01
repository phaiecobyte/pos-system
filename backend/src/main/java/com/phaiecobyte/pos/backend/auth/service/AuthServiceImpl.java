package com.phaiecobyte.pos.backend.auth.service;

import com.phaiecobyte.pos.backend.auth.dto.AuthRequest;
import com.phaiecobyte.pos.backend.auth.dto.AuthResponse;
import com.phaiecobyte.pos.backend.auth.dto.RegisterRequest;
import com.phaiecobyte.pos.backend.auth.entity.Role;
import com.phaiecobyte.pos.backend.auth.entity.User;
import com.phaiecobyte.pos.backend.auth.repository.RoleRepository;
import com.phaiecobyte.pos.backend.auth.repository.UserRepository;
import com.phaiecobyte.pos.backend.auth.security.JwtService;
import com.phaiecobyte.pos.backend.core.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public AuthResponse authenticate(AuthRequest request) {

        try {
            // ផ្ទៀងផ្ទាត់ Username និង Password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        }catch (BadCredentialsException e){
            // ចាប់កំហុសនៅពេលវាយលេខសម្ងាត់ខុស ហើយបោះសារត្រឡប់ទៅ Frontend ឱ្យបានត្រឹមត្រូវ
            throw new AppException(HttpStatus.UNAUTHORIZED, "Invalid Username or Password!");
        }

        // បើជោគជ័យ ទាញយក User រួចបង្កើត Token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwtToken = jwtService.generateToken(userDetails);

        return new AuthResponse(jwtToken);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        // ១. ពិនិត្យមើលថាតើ Username នេះមានអ្នកប្រើហើយឬនៅ?
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "ឈ្មោះអ្នកប្រើប្រាស់នេះមានរួចហើយ!");
        }

        // ២. រកមើល Role នៅក្នុង Database
        String assignRole = (request.getRoleName() != null && !request.getRoleName().isEmpty())
                ? request.getRoleName() : "CASHIER";

        Role userRole = roleRepository.findByName(assignRole)
                .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, "រកមិនឃើញសិទ្ធិ: " + assignRole));

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

        return new AuthResponse(null);
    }


}