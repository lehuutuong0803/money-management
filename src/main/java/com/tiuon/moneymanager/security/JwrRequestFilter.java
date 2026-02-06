package com.tiuon.moneymanager.security;

import com.tiuon.moneymanager.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwrRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    // This method runs on every HTTP request to validate JWT tokens and authenticate users.
    // Its purpose to intercept every incoming request, check if it has valid JWT token, and if so, authenticate the user automatically
    // without requiring login credentials again
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // extract the Authorization Header
        final String authHeader = request.getHeader("Authorization");
        String email = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("bearer ")) {
            // Extract the actual token (remove "bearer " prefix)
            jwt = authHeader.substring(7);
            // Extract username/email from the token
            email = jwtUtil.extractUsername(jwt);
        }

        // Validate and authenticate if:
        // - Email was extracted from token
        // - User is not already authenticated in this request
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //Load user details from database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            // Validate the token (check signature, expiration, etc.)
            if (jwtUtil.validateToken(jwt, userDetails)) {

                // Create authentication object
                // Credentials (null because already authenticated)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                // Add request details (IP address, session, etc.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in Spring Security context
                // This tells Spring Security: "This user is authenticated!"
                SecurityContextHolder.getContext().setAuthentication((authToken));
            }
        }
        // Continue with the request (pass to next filter or controller)
        filterChain.doFilter(request, response);
    }
}
