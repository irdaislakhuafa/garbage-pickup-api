package com.irdaislakhuafa.garbagepickupapi.models.gql.request.voucher;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.VoucherType;
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
    private String title;
    private String description;
    private MultipartFile image;
    private int pointsNeeded;
    private VoucherType type;
    private int value;
}