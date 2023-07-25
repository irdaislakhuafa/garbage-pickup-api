package com.irdaislakhuafa.garbagepickupapi.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ContactUs extends BaseEntity {
    // TODO: upload image
    @Column(nullable = false, length = 5000)
    private String image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String description;
}