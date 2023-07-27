package com.irdaislakhuafa.garbagepickupapi.models.gql.request.uservoucher;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.UserVoucherStatus;
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
public class UserVoucherFindAllByUserIdAndStatus {
    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private String userId;

    @NotNull(message = "cannot be null")
    @Builder.Default
    private List<UserVoucherStatus> statuses = new ArrayList<>();
}