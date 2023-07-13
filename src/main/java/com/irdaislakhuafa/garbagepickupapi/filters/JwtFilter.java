package com.irdaislakhuafa.garbagepickupapi.filters;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.JwtTokenExpired;
import com.irdaislakhuafa.garbagepickupapi.services.JwtService;

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
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("flitering request with JwtFilter");

        final var AUTH_HEADER_KEY = "Authorization";
        final var AUTH_PREFIX = "Bearer ";

        // validate the token
        var AUTH_HEADER = request.getHeader(AUTH_HEADER_KEY);
        if (AUTH_HEADER != null) {
            if (!AUTH_HEADER.isBlank() || !AUTH_HEADER.isEmpty()) {
                if (AUTH_HEADER.startsWith(AUTH_PREFIX)) {
                    // TODO: validate token here
                    final var token = AUTH_HEADER.substring(AUTH_PREFIX.length());
                    final var claims = this.jwtService.validateAndGetClaims(token);

                    final var isValid = this.jwtService.isValid(token, claims);
                    final var isExpired = this.jwtService.isExpired(token, claims);

                    if (isExpired) {
                        final var formatter = new SimpleDateFormat("DD/MM/YYYY HH:MM:ss");
                        final var expiredAt = formatter.format(claims.getExpiration());
                        throw new JwtTokenExpired("jwt token already expired at " + expiredAt);
                    }

                    if (isValid) {
                        // TODO: validate here
                    }

                }
            }
        }

        // go to next filter
        filterChain.doFilter(request, response);
    }
}
