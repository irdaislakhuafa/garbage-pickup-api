package com.irdaislakhuafa.garbagepickupapi.models.gql.request.uservoucher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserVoucherExchangeRequest {
    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private String userId;

    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private List<String> listId = new ArrayList<>();
}