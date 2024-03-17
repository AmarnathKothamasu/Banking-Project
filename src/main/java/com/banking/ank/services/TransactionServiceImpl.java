package com.banking.ank.services;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.ank.dto.TransferMoneyDto;
import com.banking.ank.entities.TransactionDetails;
import com.banking.ank.repostiories.AccountdetailsRepository;
import com.banking.ank.repostiories.TransactionDetailsRepository;
import com.banking.ank.util.BadRequestException;
import com.banking.ank.util.JwtUtil;
import com.banking.ank.util.RestConstants;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TransactionServiceImpl implements TransactionService {

	private static final Logger log = LogManager.getLogger(TransactionServiceImpl.class);

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AccountdetailsRepository accountdetailsRepository;
	
	@Autowired
	private AccountDetailsService accountDetailsService;

	@Autowired
	private TransactionDetailsRepository transactionDetailsRepository;

	@Override
	public String transferMoney(TransferMoneyDto transferMoneyDto, HttpServletRequest request) {

		String token = request.getHeader("Authorization").substring(7);
		String username = jwtUtil.extractUsername(token);
		String accountFrom = accountDetailsService.getAccountFromToken(username);
		String accountTo = transferMoneyDto.getTransferTo();

		if (accountTo == null || !accountdetailsRepository.existsByAccountno(accountTo)) {
			log.info("Account not found with account no : {}", transferMoneyDto.getTransferTo());
			throw new BadRequestException("Account not found with account no : " + transferMoneyDto.getTransferTo());
		}
		if (accountTo.equals(accountFrom)) {
			throw new BadRequestException("Can not transfer yourself");
		}
		
		if (getBalance(accountFrom) < transferMoneyDto.getAmount()) {
			log.error("Not enough balance in account : {}", accountFrom);
			throw new BadRequestException("Not enough balance");
		}
		return makeTransaction(accountFrom, accountTo, transferMoneyDto.getAmount());
	}

	private String makeTransaction(String accountFrom, String accountTo, Long amount) {
		// Deducting from accountFrom
		accountDetailsService.updateBalance(accountFrom, amount);
		// adding funds to accounTo
		accountDetailsService.updateBalance(accountTo, -amount);
		// Making an entry in TransactionDetailsTable

		TransactionDetails transactionDetails = new TransactionDetails();
//
//		Random random = new Random();
//		long randomNumber = random.nextLong() % 10000000000L;
//		if (randomNumber < 0) {
//			randomNumber *= -1;
//		}

//		transactionDetails.setTransactionid(randomNumber);
		transactionDetails.setAmount(amount);
		transactionDetails.setTransferFrom(accountFrom);
		transactionDetails.setTransferTo(accountTo);
		transactionDetails.setDate(new Date());
		TransactionDetails tr = transactionDetailsRepository.save(transactionDetails);
		return RestConstants.TRANSACTION_DONE;
	}

	private int getBalance(String accountFrom) {
		return accountDetailsService.getBalance(accountFrom);
	}

	@Override
	public List<TransactionDetails> getTransactionDetails(HttpServletRequest request) {
		try {
			String token = request.getHeader("Authorization").substring(7);
			String username = jwtUtil.extractUsername(token);
			String account = accountDetailsService.getAccountFromToken(username);
			return transactionDetailsRepository.findByTransferFromOrTransferTo(account);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
