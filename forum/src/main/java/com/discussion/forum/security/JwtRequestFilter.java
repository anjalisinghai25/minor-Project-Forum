package com.discussion.forum.security;

import com.discussion.forum.entities.User;
import com.discussion.forum.repository.UserRepository;
import com.discussion.forum.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtility;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authToken = request.getHeader("Authorization");

        if (authToken != null) {
            try {
                Optional<User> optional = userRepository.findById(jwtUtility.extractUserId(authToken));
                if (optional.isEmpty() || !jwtUtility.validateToken(authToken, optional.get())) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    objectMapper.writeValue(response.getWriter(), Map.of("message", "Invalid Token", "status", HttpServletResponse.SC_UNAUTHORIZED));
                } else {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(optional.get(), null, null);
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    chain.doFilter(request, response);
                    return;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        chain.doFilter(request, response);
    }

}
