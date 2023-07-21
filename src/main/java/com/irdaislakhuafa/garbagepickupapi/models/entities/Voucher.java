package com.irdaislakhuafa.garbagepickupapi.models.entities;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.VoucherType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "voucher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Voucher extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    //	TODO: upload file image
    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    @Builder.Default
    private int pointsNeeded = 0;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private VoucherType type;

    @Column(nullable = false)
    private int value;

}