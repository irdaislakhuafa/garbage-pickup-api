package com.irdaislakhuafa.garbagepickupapi.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@Entity(name = "user")
public class User extends BaseEntity {
	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String image;

	@Column(nullable = false, unique = true)
	private String phone;

	@Column(nullable = false)
	@Builder.Default
	private int saldo = 0;

	@Column(nullable = false)
	@Builder.Default
	private int point = 0;
}
