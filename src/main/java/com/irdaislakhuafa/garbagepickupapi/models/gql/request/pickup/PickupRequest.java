package com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.PickupStatus;
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
public class PickupRequest {
    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private String userId;

    @NotNull(message = "cannot be null")
    private PickupStatus status;

    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private String place;

    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private String trashTypeId;

    @NotNull(message = "cannot be null")
    private Integer weight;

    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private String description;

    private String userVoucherId;

    @NotNull(message = "cannot be null")
    private Double lat;

    @NotNull(message = "cannot be null")
    private Double lng;
}