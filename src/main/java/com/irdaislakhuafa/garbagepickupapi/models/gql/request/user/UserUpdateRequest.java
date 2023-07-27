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
public class UserUpdateRequest {
    @NotBlank(message = "cannot be blank, this field is mandatory for update")
    @NotNull(message = "cannot be null, this field is mandatory for update")
    @NotEmpty(message = "cannot be empty, this field is mandatory for update")
    private String id;

    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private String name;

    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    @Email(message = "format email is not valid")
    private String email;

    private MultipartFile image;

    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private String phone;

    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private String address;

    @NotNull(message = "cannot be null")
    @Builder.Default
    private Integer saldo = 0;

    @NotNull(message = "cannot be null")
    @Builder.Default
    private Integer point = 0;

    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    @Builder.Default
    private ArrayList<UserRoles> roles = new ArrayList<>();

    @NotNull(message = "cannot be null")
    private Boolean isDeleted;
}