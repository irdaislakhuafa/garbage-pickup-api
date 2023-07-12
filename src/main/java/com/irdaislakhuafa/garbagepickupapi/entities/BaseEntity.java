package com.irdaislakhuafa.garbagepickupapi.repository.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@MappedSuperclass
@Data
public class BaseEntity {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
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
