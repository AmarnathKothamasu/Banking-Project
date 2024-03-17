package com.banking.ank.services;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.ank.dto.LoanDto;
import com.banking.ank.entities.Loan;
import com.banking.ank.repostiories.LoanRepository;
import com.banking.ank.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class LoanServiceImpl implements LoanService {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AccountDetailsService accountDetailsService;

	@Autowired
	private LoanRepository loanRepository;

	@Override
	public List<Loan> getAllLoans() {
		return loanRepository.findAll();
	}

	@Override
	public Optional<Loan> acceptLoan(Long loanId) {
		// TODO Auto-generated method stub
		Loan loan= loanRepository.getById(loanId);
		loan.setLoanid(loanId);
		loan.setLoanid(loanId);
		loan.setStatus("APPROVED");
		loan.setActive(true);
		loan.setStartDate(new Date());

		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, loan.getDays());
		calendar.add(Calendar.MONTH, loan.getMonths());

		Date endDate = calendar.getTime();
		loan.setEndDate(endDate);
		loanRepository.save(loan);
		return null;
	}

	@Override
	public Optional<Loan> rejectLoan(Long loanId) {
		Loan loan = loanRepository.getById(loanId);
		loan.setLoanid(loanId);
		loan.setStatus("REJECTED");
		loan.setActive(false);
		loan.setStartDate(new Date());
		loanRepository.save(loan);
		return null;
	}

	@Override
	public List<Loan> getAllLoansById(HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
		String username = jwtUtil.extractUsername(token);
		String account = accountDetailsService.getAccountFromToken(username);
		return null;
	}

	@Override
	public Loan applyLoan(LoanDto loandto, HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
		String username = jwtUtil.extractUsername(token);
		String account = accountDetailsService.getAccountFromToken(username);

		Loan loan = new Loan();
		loan.setAccountno(account);
		loan.setActive(false);
		loan.setStatus("INITIATED");
		loan.setAmount(loandto.getAmount());
		loan.setInterestRate(loandto.getInterestRate());
		loan.setDays(loandto.getDays());
		loan.setMonths(loandto.getMonths());
		return loanRepository.save(loan);
	}

}
