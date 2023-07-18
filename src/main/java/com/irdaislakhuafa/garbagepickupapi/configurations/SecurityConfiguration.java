package com.irdaislakhuafa.garbagepickupapi.configurations;

import com.irdaislakhuafa.garbagepickupapi.filters.JwtFilter;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtFilter jwtFilter;

    @Bean
    public DefaultSecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider provider) throws Exception {

        // disable csrf
        http.csrf(t -> {
            t.disable();
        });

        // permit all request to url/uri and user method security instead
        http.authorizeHttpRequests(t -> {
            t.anyRequest().permitAll();
        });

        // set authentication provider
        http.authenticationProvider(provider);

        // disable session because we use jwt auth token
        http.sessionManagement(s -> {
            s.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        // added jwt filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider provider) throws Exception {
        final var manager = new ProviderManager(provider);
        return manager;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        final var provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}