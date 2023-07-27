package com.irdaislakhuafa.garbagepickupapi.models.gql.request.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RoleRequest {
    @NotBlank(message = "cannot be blank")
    @NotEmpty(message = "cannot be empty")
    @NotNull(message = "cannot be null")
    private String name;

    @NotBlank(message = "cannot be blank")
    @NotEmpty(message = "cannot be empty")
    @NotNull(message = "cannot be null")
    private String description;
}