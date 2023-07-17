package com.irdaislakhuafa.garbagepickupapi.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import com.irdaislakhuafa.garbagepickupapi.services.UserServiceTest;

@SpringBootTest
@Tags(value = {
		@Tag(value = "UserServiceTest")
})
@ExtendWith(value = MockitoExtension.class)
public class UserServiceTestImpl implements UserServiceTest<User> {
	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService<User> userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private static final User userNew = User.builder()
			.name("example user name")
			.email("example@gmail.com")
			.password("example user password")
			.image("https://empty.com")
			.phone("080000000000")
			.address("example user address")
			.saldo(0)
			.point(0)
			.build();
	private static final User user = User.builder()
			.id("ID")
			.name("example user name")
			.email("example@gmail.com")
			.password("$2a$12$W8JjLeHIhiROqoAtbS9Dse5LeDSdebn/A6iMkevn4kGygHWaliCRO")
			.image("https://empty.com")
			.phone("080000000000")
			.address("example user address")
			.saldo(0)
			.point(0)
			.build();

	@Override@Test@Disabled
	public void testSave() {
		// test save success
		when(this.userRepository.save(any(User.class))).thenReturn(user);
		var savedUser = this.userService.save(userNew);
		assertTrue(savedUser.get().getId().equals(user.getId()));
reset(this.userRepository);

		// test save with data already exists and will throw exception
		when(this.userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);
		assertThrows(DataIntegrityViolationException.class, () -> {
			this.userService.save(any(User.class));
		});
	}

	@Override
	public void testUpdate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'testUpdate'");
	}

	@Override
	public void testFromRequestToEntity() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'testFromRequestToEntity'");
	}

	@Override
	public void testGetCurrentUser() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'testGetCurrentUser'");
	}

	@Override
	public void testFromUpdateRequestToEntity() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'testFromUpdateRequestToEntity'");
	}

}
