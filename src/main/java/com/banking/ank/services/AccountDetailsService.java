package com.banking.ank.services;

import com.banking.ank.entities.AccountDetails;

public interface AccountDetailsService {

	AccountDetails createAccount(String username);

	String getAccountFromToken(String username);

	int getBalance(String accountFrom);

	void updateBalance(String accountFrom, long l);
}
