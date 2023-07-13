package com.irdaislakhuafa.garbagepickupapi.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.JwtService;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService<User> {
	private final UserRepository userRepository;

	@Value(value = "${jwt.secret-key}")
	private String secretKey;

	@Value(value = "${jwt.token.expired-in.minute}")
	private int expiredInMinute;

	@Override
	public String generateTokenString(User claimsRequest) {
		final var createdAt = new Date(System.currentTimeMillis());
		final var expiredAt = new Date(System.currentTimeMillis() + ((1000L * 60) * expiredInMinute));
		final var claims = new HashMap<String, Object>();

		claims.put("roles", claimsRequest.getRoles()
				.stream()
				.map(v -> v.getName().toUpperCase())
				.collect(Collectors.toSet()));

		final var result = Jwts.builder()
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.setClaims(claims)
				.setId(claimsRequest.getId())
				.setIssuedAt(createdAt)
				.setExpiration(expiredAt)
				.compact();

		return result;
	}

	@Override
	public Claims getClaims(String tokenString) {
		final var claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(tokenString)
				.getBody();
		return claims;
	}

	@Override
	public boolean isExpired(String tokenString) {
		final var claims = this.getClaims(tokenString);
		final var isExpired = (claims.getExpiration().before(new Date(System.currentTimeMillis())));
		return isExpired;
	}

	@Override
	public boolean isValid(String tokenString) {
		final var claims = this.getClaims(tokenString);
		final var user = this.userRepository.findById(claims.getId()).orElseThrow(() -> {
			throw new UsernameNotFoundException("user not found");
		});
		return user.isEnabled();
	}

}
