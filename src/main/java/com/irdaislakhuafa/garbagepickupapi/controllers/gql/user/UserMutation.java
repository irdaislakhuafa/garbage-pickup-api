package com.irdaislakhuafa.garbagepickupapi.controllers.gql.user;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.JwtTokenResponse;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserLoginRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.services.AuthService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@SchemaMapping(typeName = "UserMutation")
@RequiredArgsConstructor
public class UserMutation {
    private final UserService<User> userService;
    private final AuthService loginAndRegisterService;

    @SchemaMapping
    public Optional<User> save(@Argument(name = "request") UserRequest request) {
        final var user = this.userService.fromRequestToEntity(request);
        final var result = this.userService.save(user);
        return result;
    }

    @SchemaMapping
    public Optional<JwtTokenResponse> login(@Argument(name = "request") UserLoginRequest request) {
        final var result = this.loginAndRegisterService.login(request);
        return Optional.ofNullable(result);
    }

    @SchemaMapping
    public Optional<User> update(@Argument(value = "request") UserUpdateRequest request) {
        final var user = this.userService.fromUpdateRequestToEntity(request);
        final var result = this.userService.update(user);
        return result;
    }

}