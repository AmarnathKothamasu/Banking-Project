package com.banking.ank.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.ank.controllers.AccountController;
import com.banking.ank.dto.FixedDepositDto;
import com.banking.ank.entities.AccountDetails;
import com.banking.ank.entities.FixedDeposit;
import com.banking.ank.repostiories.AccountdetailsRepository;
import com.banking.ank.repostiories.FixedDepositRepository;
import com.banking.ank.util.BadRequestException;
import com.banking.ank.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class FixedDepositServiceImpl implements FixedDepositService {

	private static final Logger log = LogManager.getLogger(FixedDepositServiceImpl.class);

	@Autowired
	private AccountdetailsRepository accountdetailsRepository;

	@Autowired
	private FixedDepositRepository fixedDepositRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AccountDetailsService accountDetailsService;

	@Override
	public FixedDeposit applyfixedDeposit(FixedDepositDto fixedDepositDto, HttpServletRequest request) {

		String token = request.getHeader("Authorization").substring(7);
		String username = jwtUtil.extractUsername(token);
		String account = accountDetailsService.getAccountFromToken(username);
		if (getBalance(account) >= fixedDepositDto.getAmount()) {
			FixedDeposit fixedDeposit = new FixedDeposit();
			fixedDeposit.setAccountId(account);
			fixedDeposit.setAmount(fixedDepositDto.getAmount());
			fixedDeposit.setInterestRate(fixedDepositDto.getInterestRate());
			fixedDeposit.setStartDate(fixedDepositDto.getStartDate());
			fixedDeposit.setStatus("ACTIVE");
			
			updateBalance(account, fixedDepositDto.getAmount());
			return fixedDepositRepository.save(fixedDeposit);
		} else {
			log.error("Not enough balance for Fixed Deposit : {}", account);
			throw new BadRequestException("Not enough balance for Fixed Deposit");
		}

	}

	private int getBalance(String accountFrom) {
		return accountDetailsService.getBalance(accountFrom);
	}

	public void updateBalance(String account, long amount) {
		// TODO Auto-generated method stub
		AccountDetails accDetails = accountdetailsRepository.findByAccountno(account);
		accDetails.setBalance(accDetails.getBalance() - amount);
		accountdetailsRepository.save(accDetails);

	}

	@Override
	public List<FixedDeposit> getFixedDepositsForAccount(HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
		String username = jwtUtil.extractUsername(token);
		String account = accountDetailsService.getAccountFromToken(username);
		return fixedDepositRepository.getByaccountId(account);
	}

}
