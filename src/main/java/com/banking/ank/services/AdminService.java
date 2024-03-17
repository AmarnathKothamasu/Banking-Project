package com.banking.ank.services;

import com.banking.ank.dto.DepositORWithdrawDto;
import com.banking.ank.entities.User;

public interface AdminService {

	void depositAmount(DepositORWithdrawDto depositORWithdrawDto);

	void withDrawAmount(DepositORWithdrawDto depositORWithdrawDto);

	void createNewAdminUser(User user);

}
