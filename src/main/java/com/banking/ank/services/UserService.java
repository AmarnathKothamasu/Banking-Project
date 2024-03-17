package com.banking.ank.services;

import java.util.List;
import java.util.Optional;

import com.banking.ank.entities.User;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
	List<User> getAllUsers();

	User getUserById(Long UserId);

	String deleteUserById(Long UserId);
	
	User saveUser(User user);

	Optional<User> getUserById(HttpServletRequest request);
}
