package com.banking.ank.services;

import java.util.List;

import com.banking.ank.dto.FixedDepositDto;
import com.banking.ank.entities.FixedDeposit;

import jakarta.servlet.http.HttpServletRequest;

public interface FixedDepositService {

	FixedDeposit applyfixedDeposit(FixedDepositDto fixedDepositDto, HttpServletRequest request);

	List<FixedDeposit> getFixedDepositsForAccount(HttpServletRequest request);

}
