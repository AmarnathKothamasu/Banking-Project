package com.banking.ank.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fixeddepositrate")
public class FixedDepositRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fixedDepositRateid;


	private int timePeriodDays; // in days
    private int timePeriodMonths; // in months
    private double interestRate;

    // Constructors, getters, and setters
    public FixedDepositRate() {
    }

    public FixedDepositRate(int timePeriodDays, int timePeriodMonths, double interestRate) {
        this.timePeriodDays = timePeriodDays;
        this.timePeriodMonths = timePeriodMonths;
        this.interestRate = interestRate;
    }

    // Getters and Setters
    public Long getFixedDepositRateid() {
		return fixedDepositRateid;
	}

	public void setFixedDepositRateid(Long fixedDepositRateid) {
		this.fixedDepositRateid = fixedDepositRateid;
	}

    public int getTimePeriodDays() {
        return timePeriodDays;
    }

    public void setTimePeriodDays(int timePeriodDays) {
        this.timePeriodDays = timePeriodDays;
    }

    public int getTimePeriodMonths() {
        return timePeriodMonths;
    }

    public void setTimePeriodMonths(int timePeriodMonths) {
        this.timePeriodMonths = timePeriodMonths;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
