package com.irdaislakhuafa.garbagepickupapi.services;

import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;

public interface JwtService<CLAIMS> {
	public String generateTokenString(CLAIMS claimsRequest);

	public Claims getClaims(String tokenString);

	public boolean isExpired(String tokenString);

	public boolean isValid(String tokenString);
}
