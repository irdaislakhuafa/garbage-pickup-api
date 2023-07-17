package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.gql.JwtTokenResponse;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserLoginRequest;

public interface AuthService {
	JwtTokenResponse login(UserLoginRequest request);

}
