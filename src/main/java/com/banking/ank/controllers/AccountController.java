package com.banking.ank.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.ank.entities.AccountDetails;
import com.banking.ank.services.AccountDetailsService;
import com.banking.ank.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class AccountController {
	private static final Logger log = LogManager.getLogger(AccountController.class);
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AccountDetailsService accountDetailsService;

	@PostMapping("/createaccount")
	public ResponseEntity<String> getAccountDetails(HttpServletRequest request, HttpServletResponse response) {
		
		
		String token = request.getHeader("Authorization").substring(7);
		String username = jwtUtil.extractUsername(token);
		
		AccountDetails accountDetails = new AccountDetails();
		
		AccountDetails accountDetailsresponse;
		try {
			log.debug("Entering the createAccount Api endpoint");
			accountDetailsresponse = accountDetailsService.createAccount(username);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body("Account created successfully with account number " + accountDetailsresponse.getAccountno());
	}
	
	@GetMapping("/getBalance")
	public ResponseEntity<AccountDetails> getBalance (HttpServletRequest request, HttpServletResponse response){
		String token = request.getHeader("Authorization").substring(7);
		String username = jwtUtil.extractUsername(token);
		AccountDetails accountDetails =  new AccountDetails();
		try {
			log.debug("Entering the getBalance Api endpoint");
			accountDetails = accountDetailsService.getBalanceDetails(username);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(accountDetails);
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(accountDetails);
	}
}
