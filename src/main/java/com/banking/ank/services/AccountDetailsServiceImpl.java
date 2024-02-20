package com.banking.ank.services;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.ank.entities.AccountDetails;
import com.banking.ank.entities.User;
import com.banking.ank.repostiories.AccountdetailsRepository;
import com.banking.ank.repostiories.UserRepository;
import com.banking.ank.util.JwtUtil;

@Service
public class AccountDetailsServiceImpl implements AccountDetailsService {

	@Autowired
	private AccountdetailsRepository accountdetailsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public AccountDetails createAccount(String name) {
		Long userID = userRepository.findUserIdByEmail(name);
		Long accountId = accountdetailsRepository.findAccountIdByEmail(name);
		if (accountId == null) {
			AccountDetails accountDetails = new AccountDetails();
			accountDetails.setUser(userRepository.getById(userID));
			accountDetails.setAccountno(generateAccountNumber());
			accountDetails.setEmail(name);
			accountDetails.setBalance(null);
			return accountdetailsRepository.save(accountDetails);
		} else {
			throw new RuntimeException("You have an account already. Cannot create a new one!!");
		}
	}

	public String generateAccountNumber() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		int ACCOUNT_NUMBER_LENGTH = 10;
		String DIGITS = "0123456789";
		// Generate a random account number until a unique one is found
		do {
			for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
				int index = random.nextInt(DIGITS.length());
				sb.append(DIGITS.charAt(index));
			}
		} while (!isUnique(sb.toString()));

		return sb.toString();
	}

	private boolean isUnique(String accountNo) {
		return !accountdetailsRepository.existsByAccountno(accountNo);
	}

	public String getAccountFromToken(String token) {
//		String email = jwtUtil.getUsernameFromToken(token);
		return accountdetailsRepository.findAccountnoByEmail(token);
	}

	@Override
	public int getBalance(String accountFrom) {
		return accountdetailsRepository.findBalanceByAccountno(accountFrom);
	}

	@Override
	public void updateBalance(String account, long amount) {
		// TODO Auto-generated method stub
		AccountDetails accDetails = accountdetailsRepository.findByAccountno(account);
		accDetails.setBalance(accDetails.getBalance() - amount);
		accountdetailsRepository.save(accDetails);

	}

	@Override
	public AccountDetails getBalanceDetails(String username) {
		AccountDetails accDetails = accountdetailsRepository.findByEmail(username);
		return accDetails;
	}
}
