package com.irdaislakhuafa.garbagepickupapi.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "`role`")
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {
	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = true, unique = false)
	private String description;
}
