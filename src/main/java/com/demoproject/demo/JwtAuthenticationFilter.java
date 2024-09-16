package com.demoproject.demo;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
            throws jakarta.servlet.ServletException, IOException {
                final String authorizationHeader = request.getHeader("Authorization");

                String username = null;
                String token = null;
        
                if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                    token = authorizationHeader.substring(7);
                    try {
                        username = jwtUtil.extractUsername(token);
                    } catch (Exception e) {
                        // Handle token parsing exceptions
                    }
                }
        
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtil.validateToken(token, username)) {
                        // Here you would normally load user details and create an authentication token
                        // For example:
                        // UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        // UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        // SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
        
                filterChain.doFilter(request, response);
    }
}