package com.banking.ank.dto;

import java.math.BigDecimal;
import java.util.Date;

public class FixedDepositDto {

	private Long amount;

	private BigDecimal interestRate;
	private Date startDate;

	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
}
