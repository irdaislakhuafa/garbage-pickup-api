package com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.PickupStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PickupFindAllByUserIdWithRangeDateRequest {
    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private String userId;

    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    @Schema(format = "date", description = "dd/MM/yyyy HH:mm:ss")
    private String start;

    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    @Schema(format = "date", description = "dd/MM/yyyy HH:mm:ss")
    private String end;

    @Builder.Default
    private List<PickupStatus> statuses = new ArrayList<>();
}