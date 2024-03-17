package com.banking.ank.repostiories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banking.ank.entities.Loan;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

	//Optional<Loan> findByLoanid(long loanid);
	
//	@Query("SELECT l FROM loans l WHERE l.loanid = :loanid")
//	Loan findByLoanId(@Param("loanid") long loanid);
}
