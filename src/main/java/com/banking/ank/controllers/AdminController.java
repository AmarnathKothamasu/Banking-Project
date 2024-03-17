package com.banking.ank.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.ank.dto.DepositORWithdrawDto;
import com.banking.ank.entities.User;
import com.banking.ank.services.AdminService;

@RestController
@RequestMapping("/api")
public class AdminController {

	private static final Logger log = LogManager.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;

	@PostMapping("/depositAmount")
	public ResponseEntity<?> depoistAmounnt(@RequestBody DepositORWithdrawDto depositORWithdrawDto) {
		try {
			adminService.depositAmount(depositORWithdrawDto);
		} catch (Exception e) {
			log.error("error produced during Depoisting Amount : {}", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return null;
		// return new ResponseEntity<>(newUser, HttpStatus.OK);

	}

	@PostMapping("/withdrawAmount")
	public ResponseEntity<?> withDrawAmount(@RequestBody DepositORWithdrawDto depositORWithdrawDto) {
		try {
			adminService.withDrawAmount(depositORWithdrawDto);
		} catch (Exception e) {
			log.error("error produced during withDrawAmount Amount : {}", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return null;
		// return new ResponseEntity<>(newUser, HttpStatus.OK);

	}

	@PostMapping("/createNewAdminUser")
	public ResponseEntity<?> createNewAdminUser(@RequestBody User user) {
		User newUser = user;
		try {
			adminService.createNewAdminUser(user);
		} catch (Exception e) {
			log.error("error produced during withDrawAmount Amount : {}", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newUser, HttpStatus.OK);

	}

}
