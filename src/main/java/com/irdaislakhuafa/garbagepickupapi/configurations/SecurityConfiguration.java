package com.irdaislakhuafa.garbagepickupapi.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.irdaislakhuafa.garbagepickupapi.filters.JwtFilter;
import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserRequest;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
	private final BCryptPasswordEncoder passwordEncoder;
	private final UserService<User, UserRequest> userService;
	private final JwtFilter jwtFilter;

	@Bean
	public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// disable csrf
		http.csrf(t -> {
			t.disable();
		});

		// permit all request to url/uri and user method security instead
		http.authorizeHttpRequests(t -> {
			t.anyRequest().permitAll();
		});

		// set authentication provider
		http.authenticationProvider(new DaoAuthenticationProvider() {
			{
				setPasswordEncoder(passwordEncoder);
				setUserDetailsService(userService);
			}
		});

		// disable session because we use jwt auth token
		http.sessionManagement(s -> {
			s.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		});

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}
}
