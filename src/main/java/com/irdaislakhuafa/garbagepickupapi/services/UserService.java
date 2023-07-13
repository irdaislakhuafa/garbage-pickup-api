package com.irdaislakhuafa.garbagepickupapi.services;

import java.util.Optional;

public interface UserService<U, R> {
	public Optional<U> save(U user);

	public U fromRequestToEntity(R request);
}
