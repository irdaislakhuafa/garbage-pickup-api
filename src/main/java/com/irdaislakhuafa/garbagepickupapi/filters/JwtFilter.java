package com.irdaislakhuafa.garbagepickupapi.filters;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(value = 1)
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("flitering request with JwtFilter");

        final var AUTH_HEADER_KEY = "Authorization";
        final var AUTH_PREFIX = "Bearer";

        // validate the token
        var AUTH_HEADER = request.getHeader(AUTH_HEADER_KEY);
        if (AUTH_HEADER != null) {
            if (!AUTH_HEADER.isBlank() || !AUTH_HEADER.isEmpty()) {
                if (AUTH_HEADER.startsWith(AUTH_PREFIX)) {
                    // TODO: validate token here
                }
            }
        }

        // go to next filter
        filterChain.doFilter(request, response);
    }
}
