package com.banking.ank.services;

import java.util.List;
import java.util.Optional;

import com.banking.ank.dto.LoanDto;
import com.banking.ank.entities.Loan;

import jakarta.servlet.http.HttpServletRequest;

public interface LoanService {

	List<Loan> getAllLoans();

	Loan applyLoan(LoanDto loandto,HttpServletRequest request);

	Optional<Loan> acceptLoan(Long loanId);

	Optional<Loan> rejectLoan(Long loanId);

	List<Loan> getAllLoansById(HttpServletRequest request);

}
