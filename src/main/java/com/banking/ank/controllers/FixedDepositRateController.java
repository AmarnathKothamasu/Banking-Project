package com.banking.ank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.banking.ank.entities.FixedDepositRate;
import com.banking.ank.services.FixedDepositRateService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FixedDepositRateController {
	@Autowired
	private FixedDepositRateService fixedDepositRateService;

	@GetMapping("/viewFixedDepositRates")
	public List<FixedDepositRate> getAllFixedDepositRates() {
		return fixedDepositRateService.getAllFixedDepositRates();
	}

	@GetMapping("/viewFixedDepositRate/{id}")
	public FixedDepositRate getFixedDepositRateById(@PathVariable Long id) {
		return fixedDepositRateService.getFixedDepositRateById(id);
	}

	@PutMapping("/viewFixedDepositRate/{id}")
	public void updateFixedDepositRate(@PathVariable Long id, @RequestBody FixedDepositRate updatedRate) {
		fixedDepositRateService.updateFixedDepositRate(id, updatedRate);
	}
}
