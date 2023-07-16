package com.irdaislakhuafa.garbagepickupapi.models.entities;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.PickupStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "pickup")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Pickup extends BaseEntity {
	@Column(nullable = false)
	private String resi;

	@JoinColumn(nullable = false)
	@ManyToOne
	private User user;

	@JoinColumn(nullable = false)
	@ManyToOne
	private User courier;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private PickupStatus status;

	@Column(nullable = true)
	private int weight; // in KG

	@Column(length = 5000, nullable = false)
	private String place;

	@JoinColumn(nullable = false)
	@ManyToOne
	private TrashType trashType;

	@Column(nullable = false)
	private String description;
}
