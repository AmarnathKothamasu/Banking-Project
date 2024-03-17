package com.banking.ank.services;

import java.util.List;

import com.banking.ank.entities.FixedDepositRate;

public interface FixedDepositRateService {

	List<FixedDepositRate> getAllFixedDepositRates();

	FixedDepositRate getFixedDepositRateById(Long id);

	void updateFixedDepositRate(Long id, FixedDepositRate updatedRate);

}
