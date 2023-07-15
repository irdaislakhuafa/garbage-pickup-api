package com.irdaislakhuafa.garbagepickupapi.models.entities;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.PickupStatus;

import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "pickup")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Pickup extends BaseEntity {
	private String resi;
	private User user;
	private User courier;
	private PickupStatus status;
	private int weight;
	private String place;
	private TrashType trashType;
	private String description;
}
