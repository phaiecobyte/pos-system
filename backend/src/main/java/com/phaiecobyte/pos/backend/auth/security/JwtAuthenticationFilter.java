package com.phaiecobyte.pos.backend.auth.security;

import io.jsonwebtoken.ExpiredJwtException; // <--- បន្ថែម Import នេះ
import io.jsonwebtoken.JwtException; // <--- បន្ថែម Import នេះ
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException
    {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        jwt = authHeader.substring(7);

        // ប្រើ Try-Catch ដើម្បីការពារ Server បោះ Error 500 ពេល Token ផុតកំណត់ ឬខូច
        try {
            username = jwtService.extractUsername(jwt);

            // ទី៣៖ បើមានឈ្មោះ User ហើយគាត់មិនទាន់បាន Authenticated នៅក្នុង System ទេ
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // ទី៤៖ ពិនិត្យមើលថា Token នៅមានសុពលភាពឬទេ
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // អនុញ្ញាតឱ្យ User ចូលប្រើប្រាស់
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (ExpiredJwtException e) {
            // Token បានផុតកំណត់ (អ្នកអាច Log វាទុកបើសិនជាចង់)
            System.out.println("JWT Token is expired: " + e.getMessage());
        } catch (JwtException e) {
            // Token ខូច ឬមិនត្រឹមត្រូវ
            System.out.println("Invalid JWT Token: " + e.getMessage());
        } catch (Exception e) {
            // កំហុសផ្សេងៗដែលអាចកើតមានកំឡុងពេលទាញយក User
            System.out.println("Error during authentication validation: " + e.getMessage());
        }

        // បន្តដំណើរការ Request ទៅកាន់ Filter បន្ទាប់
        // (បើចូល Catch ខាងលើ Authentication នឹងនៅតែ null រួច Spring Security នឹង Block វាជា 401/403)
        filterChain.doFilter(request, response);
    }
}