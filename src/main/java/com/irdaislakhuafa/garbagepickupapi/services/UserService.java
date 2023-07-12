package com.irdaislakhuafa.garbagepickupapi.services;

import java.util.Optional;

public interface UserService<U> {
	public Optional<U> save(U user);
}
