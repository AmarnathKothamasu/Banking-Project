package com.banking.ank.controllers;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.ank.dto.TransferMoneyDto;
import com.banking.ank.services.TransactionService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class TransactionController {

	private static final Logger log = LogManager.getLogger(TransactionController.class);
	@Autowired
	private TransactionService transactionService; 
	
	@PostMapping("/transfer")
	public ResponseEntity<?> transferMoney(@RequestBody TransferMoneyDto transferMoneyDto, HttpServletRequest request){
		try {
			transactionService.transferMoney(transferMoneyDto,request);
		}
		catch(Exception e) {
			log.error("error produced getting transaction history : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return null;
	}
}
