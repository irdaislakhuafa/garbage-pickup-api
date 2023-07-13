package com.irdaislakhuafa.garbagepickupapi.filters;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Component
// @Order(value = 1)
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("flitering request with JwtFilter");

        final var AUTH_HEADER_KEY = "Authorization";
        final var AUTH_BEARER = "Bearer";

        // validate the token
    }
}
