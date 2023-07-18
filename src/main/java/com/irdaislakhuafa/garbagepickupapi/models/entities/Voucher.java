package com.irdaislakhuafa.garbagepickupapi.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
}