package com.fastForm.core.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        // TODO: implement JWT token validation
        // String token = request.getHeader("Authorization");

        filterChain.doFilter(request, response);
    }

    //    @Override
    //    protected boolean shouldNotFilter(HttpServletRequest request) {
    //        String path = request.getRequestURI();
    //        List<String> publicPaths = new ArrayList<>();
    //        return publicPaths.contains(path);
    //    }

}
