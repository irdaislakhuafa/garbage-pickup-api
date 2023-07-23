package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.gql.request.user.UserLoginRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.response.JwtTokenResponse;

public interface AuthService {
    JwtTokenResponse login(UserLoginRequest request);

}