package com.irdaislakhuafa.garbagepickupapi.models.entities;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "voucher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Voucher extends BaseEntity {
	private String title;
	private String description;
	private String image;
	private int pointsNeeded;
}
