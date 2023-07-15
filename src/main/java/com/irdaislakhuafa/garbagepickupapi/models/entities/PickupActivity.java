package com.irdaislakhuafa.garbagepickupapi.models.entities;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "pickup_activity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PickupActivity extends BaseEntity {
	private Pickup pickup;
	private String description;
}
