package com.irdaislakhuafa.garbagepickupapi.exceptions.custom;

public class JwtTokenExpired extends RuntimeException {
	public JwtTokenExpired(String s) {
		super(s);
	}
}
