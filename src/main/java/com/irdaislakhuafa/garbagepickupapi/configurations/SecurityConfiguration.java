package com.irdaislakhuafa.garbagepickupapi.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
	@Bean
	public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(t -> {
			t.disable();
		});

		http.authorizeHttpRequests(t -> {
			t.anyRequest().permitAll();
		});

		return http.build();
	}
}
