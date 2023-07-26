package com.irdaislakhuafa.garbagepickupapi.models.gql.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserRequest {
    @NotBlank(message = "name: cannot be blank")
    @NotNull(message = "name: cannot be null")
    @NotEmpty(message = "name: cannot be empty")
    private String name;

    @NotBlank(message = "email: cannot be blank")
    @NotNull(message = "email: cannot be null")
    @NotEmpty(message = "email: cannot be empty")
    @Email(message = "email: format email is not valid")
    private String email;

    @NotBlank(message = "password: cannot be blank")
    @NotNull(message = "password: cannot be null")
    @NotEmpty(message = "password: cannot be empty")
    private String password;

    private MultipartFile image;

    @NotBlank(message = "phone: cannot be blank")
    @NotNull(message = "phone: cannot be null")
    @NotEmpty(message = "phone: cannot be empty")
    private String phone;

    @NotBlank(message = "address: cannot be blank")
    @NotNull(message = "address: cannot be null")
    @NotEmpty(message = "address: cannot be empty")
    private String address;

    @Builder.Default
    @NotBlank(message = "saldo: cannot be blank")
    @NotEmpty(message = "saldo: cannot be empty")
    private int saldo = 0;

    @Builder.Default
    @NotBlank(message = "point: cannot be blank")
    @NotEmpty(message = "point: cannot be empty")
    private int point = 0;

    @Builder.Default
    @NotBlank(message = "roles: cannot be blank")
    @NotNull(message = "roles: cannot be null")
    @NotEmpty(message = "roles: cannot be empty")
    private ArrayList<UserRoles> roles = new ArrayList<>();
}