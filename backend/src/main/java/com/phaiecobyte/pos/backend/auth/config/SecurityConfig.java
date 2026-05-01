package com.phaiecobyte.pos.backend.auth.config;

import com.phaiecobyte.pos.backend.auth.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // បើកដំណើរការ CORS
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // កំណត់ប្រភពណាខ្លះ (Origins) ដែលមានសិទ្ធិហៅចូល API នេះ
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // អនុញ្ញាត Frontend Origin
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        // អនុញ្ញាត Methods ដែល Frontend អាចប្រើបាន
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // អនុញ្ញាត Headers ដែលតម្រូវឱ្យមាន (ពិសេស Authorization សម្រាប់បញ្ជូន Token)
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // អនុញ្ញាតឱ្យ Frontend អាចអាន Headers ដែល Backend បោះត្រឡប់ទៅវិញ
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // អនុវត្តច្បាប់ CORS នេះទៅលើគ្រប់ Endpoints ទាំងអស់
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}