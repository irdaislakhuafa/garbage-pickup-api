package com.irdaislakhuafa.garbagepickupapi.controllers.rest;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.user.UserLoginRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.user.UserRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.response.JwtTokenResponse;
import com.irdaislakhuafa.garbagepickupapi.models.rest.response.RestResponse;
import com.irdaislakhuafa.garbagepickupapi.services.AuthService;
import com.irdaislakhuafa.garbagepickupapi.services.RestValidationService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(value = {"/api/rest/users"})
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RestValidationService restValidationService;
    private final AuthService authService;

    @Operation(
            summary = "This endpoint used to register new user",
            parameters = {
                    @Parameter(name = "image", description = "The image field is used for the user's profile photo, this field is optional, you can leave it empty")
            }
    )
    @PostMapping(value = {"/register"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<User, Map<String, Object>>> register(@RequestBody @ModelAttribute @Valid UserRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(RestResponse.<User, Map<String, Object>>builder()
                            .errors(this.restValidationService.getListErrors(errors))
                            .build());
        }
        final var user = this.userService.fromRequestToEntity(request);
        final var result = this.userService.save(user);
        return ResponseEntity.ok(RestResponse.<User, Map<String, Object>>builder()
                .data(result.get())
                .build());
    }

    @Operation(summary = "Used to login user and get jwt token to use all resource")
    @PostMapping(value = {"/login"}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<JwtTokenResponse, Map<String, Object>>> login(@RequestBody @Valid UserLoginRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(RestResponse.<JwtTokenResponse, Map<String, Object>>builder()
                            .errors(this.restValidationService.getListErrors(errors))
                            .build());
        }
        final var result = this.authService.login(request);
        return ResponseEntity.ok(RestResponse.<JwtTokenResponse, Map<String, Object>>builder()
                .data(result)
                .build());
    }
}