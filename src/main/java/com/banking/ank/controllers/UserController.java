package com.banking.ank.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.banking.ank.JwtService.UserDetailsServiceImpl;
import com.banking.ank.dto.AuthenticationDTO;
import com.banking.ank.dto.AuthenticationResponse;
import com.banking.ank.entities.User;
import com.banking.ank.services.UserService;
import com.banking.ank.services.UserServiceImpl;
import com.banking.ank.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UserController {

	private static final Logger log = LogManager.getLogger(UserController.class);

	@GetMapping("/api/hello")
	@ResponseBody
	public String hello() {
		return "Hello, World!";
	}

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private UserService userService;

	@PostMapping("/authenticate")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO,
			HttpServletResponse response)
			throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
		try {

			log.debug("Entering Authentication Function");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(),
					authenticationDTO.getPassword()));
		} catch (BadCredentialsException e) {
			log.error("Wrong credentials");
			throw new BadCredentialsException("Incorrect username or password!");
		} catch (DisabledException disabledException) {
			log.error("User is not activated");
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
			return null;
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDTO.getEmail());

		final String jwt = jwtUtil.generateToken(userDetails.getUsername());

		return new AuthenticationResponse(jwt);

	}

	@PostMapping("/user/createuser")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		User newUser = user;
		try {
			log.debug("Entering the createuser service");
			newUser = userService.saveUser(user);
		} catch (Exception e) {
			log.error("error produced during creating user : {}", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newUser, HttpStatus.OK);

	}

	@GetMapping("/api/getUserObject")
	public ResponseEntity<?> geUserObject(HttpServletRequest request) {
		Optional<User> user = Optional.of(new User());
		try {
			user = userService.getUserById(request);
		} catch (Exception e) {
			log.error("error produced during retriving user : {}", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
