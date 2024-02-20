package com.banking.ank.repostiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banking.ank.entities.AccountDetails;

@Repository
public interface AccountdetailsRepository extends JpaRepository<AccountDetails,Long>{

	boolean existsByAccountno(String accountNo);
	
	@Query("SELECT u.id FROM AccountDetails u WHERE u.email = :email")
	Long findAccountIdByEmail(String email);
	
	@Query("SELECT ac.accountno FROM AccountDetails ac WHERE ac.email = :email")
	String findAccountnoByEmail(@Param("email") String email);

	@Query("SELECT ac.balance FROM AccountDetails ac WHERE ac.accountno = :accountFrom")
	int findBalanceByAccountno(@Param("accountFrom") String accountFrom);
	
	@Query("SELECT ac FROM AccountDetails ac WHERE ac.accountno = :accountno")
	AccountDetails findByAccountno(@Param("accountno") String accountno);
	
	@Query("SELECT ac FROM AccountDetails ac WHERE ac.email = :email")
	AccountDetails findByEmail(@Param("email") String email);
}
