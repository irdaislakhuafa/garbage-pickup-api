package com.irdaislakhuafa.garbagepickupapi.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "trash_type")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class TrashType extends BaseEntity {
	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false, length = 5000)
	private String description;
}
