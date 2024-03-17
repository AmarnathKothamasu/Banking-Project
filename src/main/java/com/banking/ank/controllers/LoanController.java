package com.banking.ank.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banking.ank.dto.LoanDto;
import com.banking.ank.entities.Loan;
import com.banking.ank.services.LoanService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // Endpoint to view all loans
    @GetMapping("/getloans")
    public ResponseEntity<List<Loan>> viewLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    
    // Endpoint to view all loans by Id
    @GetMapping("/getloansbyId")
    public ResponseEntity<List<Loan>> viewLoansById(HttpServletRequest request) {
        List<Loan> loans = loanService.getAllLoansById(request);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    // Endpoint to apply for a loan
    @PostMapping("/applyloan")
    public ResponseEntity<Loan> applyLoan(@RequestBody LoanDto loandto,HttpServletRequest request) {
        Loan newLoan = loanService.applyLoan(loandto,request);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    // Endpoint to accept a loan
    @PutMapping("/acceptloan/{loanId}")
    public ResponseEntity<Loan> acceptLoan(@PathVariable Long loanId) {
        Optional<Loan> loan = loanService.acceptLoan(loanId);
        if (loan.isPresent()) {
            return new ResponseEntity<>(loan.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to reject a loan
    @PutMapping("/rejectloan/{loanId}")
    public ResponseEntity<Loan> rejectLoan(@PathVariable Long loanId) {
        Optional<Loan> loan = loanService.rejectLoan(loanId);
        if (loan.isPresent()) {
            return new ResponseEntity<>(loan.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
