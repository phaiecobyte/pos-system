package com.phaiecobyte.pos.backend.identity.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phaiecobyte.pos.backend.core.common.base.ApiResponse;
import com.phaiecobyte.pos.backend.core.tenancy.TenantContext;
import com.phaiecobyte.pos.backend.identity.repository.InvalidatedTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException
    {
        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.toLowerCase().startsWith("bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        final String jwt = authHeader.substring(7);
        if(invalidatedTokenRepository.existsById(jwt)){
            handleAuthError(
                    response,
                    HttpStatus.UNAUTHORIZED,
                    "Token has been revoked"
            );
            return;
        }
        UUID tenantId = jwtService.extractTenantId(jwt);
        TenantContext.setTenantId(tenantId);
        final String username;
        try {
            username = jwtService.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.warn("JWT Token is expired: {}", e.getMessage());
            handleAuthError(response, HttpStatus.UNAUTHORIZED, "JWT Token is expired! Please login again.");
            return;
        } catch (JwtException e) {
            log.warn("Invalid JWT Token: {}", e.getMessage());
            handleAuthError(response, HttpStatus.UNAUTHORIZED, "Invalid JWT Token!");
            return;
        } catch (Exception e) {
            log.error("Error during authentication validation: {}", e.getMessage());
            handleAuthError(response, HttpStatus.INTERNAL_SERVER_ERROR, "Authentication validation error.");
            return;
        }finally {
            TenantContext.clear();
        }

    }

    private void handleAuthError(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .message(message)
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }

    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request
    ) {
        return request.getServletPath()
                .equals("/api/v1/auth/refresh-token");
    }
}