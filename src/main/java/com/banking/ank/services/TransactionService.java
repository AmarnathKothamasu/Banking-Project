package com.banking.ank.services;

import java.util.List;

import com.banking.ank.dto.TransferMoneyDto;
import com.banking.ank.entities.TransactionDetails;

import jakarta.servlet.http.HttpServletRequest;

public interface TransactionService {

	String transferMoney(TransferMoneyDto transferMoneyDto, HttpServletRequest request);

	List<TransactionDetails> getTransactionDetails(HttpServletRequest request);
	
}
