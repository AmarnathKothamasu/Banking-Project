package com.banking.ank.services;

import com.banking.ank.dto.TransferMoneyDto;

import jakarta.servlet.http.HttpServletRequest;

public interface TransactionService {

	String transferMoney(TransferMoneyDto transferMoneyDto, HttpServletRequest request);
	
}
