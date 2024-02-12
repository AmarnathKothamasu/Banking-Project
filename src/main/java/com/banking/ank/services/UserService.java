package com.banking.ank.services;

import java.util.List;

import com.banking.ank.entities.User;

public interface UserService {
	List<User> getAllUsers();

	User getUserById(Long UserId);

	String deleteUserById(Long UserId);
	
	User saveUser(User user);
}
