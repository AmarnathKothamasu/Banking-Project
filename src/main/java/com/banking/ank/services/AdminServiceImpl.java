package com.banking.ank.services;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.ank.controllers.AdminController;
import com.banking.ank.dto.DepositORWithdrawDto;
import com.banking.ank.entities.AccountDetails;
import com.banking.ank.entities.TransactionDetails;
import com.banking.ank.entities.User;
import com.banking.ank.repostiories.AccountdetailsRepository;
import com.banking.ank.repostiories.TransactionDetailsRepository;
import com.banking.ank.util.BadRequestException;
import com.banking.ank.util.RestConstants;

@Service
public class AdminServiceImpl implements AdminService {

	private static final Logger log = LogManager.getLogger(AdminServiceImpl.class);

	@Autowired
	private AccountdetailsRepository accountdetailsRepository;

	@Autowired
	private TransactionDetailsRepository transactionDetailsRepository;

	@Autowired
	private AccountDetailsService accountDetailsService;

	@Override
	public void depositAmount(DepositORWithdrawDto depositORWithdrawDto) {

		if (depositORWithdrawDto.getAccountNo() == null
				|| !accountdetailsRepository.existsByAccountno(depositORWithdrawDto.getAccountNo())) {
			log.info("Account not found with account no : {}", depositORWithdrawDto.getAccountNo());
			throw new BadRequestException("Account not found with account no : " + depositORWithdrawDto.getAccountNo());
		}

		AccountDetails accDetails = accountdetailsRepository.findByAccountno(depositORWithdrawDto.getAccountNo());
		accDetails.setBalance(accDetails.getBalance() + depositORWithdrawDto.getAmount());
		accountdetailsRepository.save(accDetails);

		TransactionDetails transactionDetails = new TransactionDetails();
		transactionDetails.setAmount(depositORWithdrawDto.getAmount());
		transactionDetails.setTransferFrom("deposit");
		transactionDetails.setTransferTo(depositORWithdrawDto.getAccountNo());
		transactionDetails.setDate(new Date());
		TransactionDetails tr = transactionDetailsRepository.save(transactionDetails);
		return;
	}

	@Override
	public void withDrawAmount(DepositORWithdrawDto depositORWithdrawDto) {

		if (depositORWithdrawDto.getAccountNo() == null
				|| !accountdetailsRepository.existsByAccountno(depositORWithdrawDto.getAccountNo())) {
			log.info("Account not found with account no : {}", depositORWithdrawDto.getAccountNo());
			throw new BadRequestException("Account not found with account no : " + depositORWithdrawDto.getAccountNo());
		}

		if (getBalance(depositORWithdrawDto.getAccountNo()) < depositORWithdrawDto.getAmount()) {
			log.error("Not enough balance in account : {}", depositORWithdrawDto.getAccountNo());
			throw new BadRequestException("Not enough balance");
		}

		AccountDetails accDetails = accountdetailsRepository.findByAccountno(depositORWithdrawDto.getAccountNo());
		accDetails.setBalance(accDetails.getBalance() - depositORWithdrawDto.getAmount());
		accountdetailsRepository.save(accDetails);

		TransactionDetails transactionDetails = new TransactionDetails();
		transactionDetails.setAmount(depositORWithdrawDto.getAmount());
		transactionDetails.setTransferFrom("withdraw");
		transactionDetails.setTransferTo(depositORWithdrawDto.getAccountNo());
		transactionDetails.setDate(new Date());
		TransactionDetails tr = transactionDetailsRepository.save(transactionDetails);
		return;

	}

	@Override
	public void createNewAdminUser(User user) {

	}

	private int getBalance(String accountFrom) {
		return accountDetailsService.getBalance(accountFrom);
	}

}
