package com.irdaislakhuafa.garbagepickupapi.models.gql.request.voucher;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.VoucherType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VoucherRequest {
    @NotBlank(message = "cannot be blank")
    @NotEmpty(message = "cannot be empty")
    @NotNull(message = "cannot be null")
    private String title;

    @NotBlank(message = "cannot be blank")
    @NotEmpty(message = "cannot be empty")
    @NotNull(message = "cannot be null")
    private String description;

    private MultipartFile image;

    @NotNull(message = "cannot be null")
    private Integer pointsNeeded;

    @NotNull(message = "cannot be null")
    private VoucherType type;

    @NotNull(message = "cannot be null")
    private Integer value;
}