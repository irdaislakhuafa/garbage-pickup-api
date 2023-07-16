package com.irdaislakhuafa.garbagepickupapi.models.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "pickup_activity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class PickupActivity extends BaseEntity {
	@JoinColumn(nullable = false)
	@ManyToOne
	private Pickup pickup;

	@Column(nullable = false, length = 5000)
	private String description;
}
