package com.banking.ank.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.ank.entities.User;
import com.banking.ank.repostiories.UserRepository;
import com.banking.ank.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public List<User> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@Override
	public User getUserById(Long UserId) {
		User user = userRepository.getById(UserId);
		return user;
	}

	@Override
	public String deleteUserById(Long UserId) {
		User user = userRepository.getById(UserId);
		if (user.getName() != null) {
			userRepository.deleteById(UserId);
			return "User has been succesfully deleted";
		}
		return null;
	}
	
	@Override
	public User saveUser(User user) {
		
		Optional<User> EmailCheck = userRepository.findByEmail(user.getEmail());
		
		if(!EmailCheck.isEmpty()) {
			throw new RuntimeException("User with email already exists :" + user.getEmail());
		}
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setAge(new java.util.Date().getYear() - user.getDob().getYear());
		User savedUser = userRepository.save(user);
		return savedUser;
	}

	@Override
	public Optional<User> getUserById(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization").substring(7);
		String username = jwtUtil.extractUsername(token);
		Optional<User> user = userRepository.findByEmail(username);
		return user;
	}
}
