package com.irdaislakhuafa.garbagepickupapi.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private String createdBy;

	@Column(nullable = true)
	private LocalDateTime updatedAt;

	@Column(nullable = true)
	private String updatedBy;

	@Column(nullable = true)
	private LocalDateTime deletedAt;

	@Column(nullable = true)
	private String deletedBy;

	@Builder.Default
	@Column(nullable = false)
	private boolean isDeleted = false;

	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	@PreRemove
	public void preRemove() {
		this.deletedAt = LocalDateTime.now();
	}
}
