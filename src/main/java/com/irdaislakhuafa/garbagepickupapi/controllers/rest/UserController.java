package com.irdaislakhuafa.garbagepickupapi.controllers.rest;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.user.UserRequest;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = {"api/rest/users"})
@RestController
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping(value = {"/register"})
    public ResponseEntity<User> register(@RequestBody UserRequest request, Errors errors) {
        final var user = this.userService.fromRequestToEntity(request);
        final var result = this.userService.save(user);
        return ResponseEntity.ok(result.get());
    }
}