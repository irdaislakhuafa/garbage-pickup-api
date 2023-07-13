package com.irdaislakhuafa.garbagepickupapi.services;

import io.jsonwebtoken.Claims;

public interface JwtService<CLAIMS> {
	public String generateTokenString(CLAIMS claimsRequest);

	public Claims validateAndGetClaims(String tokenString);

	public boolean isExpired(String tokenString);

	public boolean isExpired(String tokenString, Claims claims);

	public boolean isValid(String tokenString);

	public boolean isValid(String tokenString, Claims claims);
}
