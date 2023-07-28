package com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickupactivity;

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
public class PickupActivityRequest {
    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private String pickupId;

    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private String description;
}