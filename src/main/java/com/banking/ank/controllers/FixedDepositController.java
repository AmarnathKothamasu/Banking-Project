package com.banking.ank.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.ank.dto.FixedDepositDto;
import com.banking.ank.entities.FixedDeposit;
import com.banking.ank.services.FixedDepositService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class FixedDepositController {
	
	private static final Logger log = LogManager.getLogger(FixedDepositController.class);

	@Autowired
	private FixedDepositService fixedDepositService;  
	
	@PostMapping("/applyFixedDeposit")
	public ResponseEntity<?> applyFixedDeposit(@RequestBody FixedDepositDto fixedDepositDto, HttpServletRequest request){
		FixedDeposit fd = new FixedDeposit();
		try {
			fd = fixedDepositService.applyfixedDeposit(fixedDepositDto,request);
		}
		catch(Exception e) {
			log.error("error produced during applyFixedDeposit : {}", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(fd, HttpStatus.OK);
	}
	
	@GetMapping("/getFixedDepositsForAccount")
	public ResponseEntity<?> getFixedDepositsForAccount(HttpServletRequest request){
		List<FixedDeposit> fds = fixedDepositService.getFixedDepositsForAccount(request);
		return new ResponseEntity<>(fds, HttpStatus.OK);
	}

}
