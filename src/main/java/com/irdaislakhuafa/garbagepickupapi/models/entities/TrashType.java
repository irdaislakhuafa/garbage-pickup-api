package com.irdaislakhuafa.garbagepickupapi.models.entities;

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
	private String name;
	private String description;
}
