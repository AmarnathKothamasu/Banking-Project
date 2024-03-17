package com.banking.ank.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.ank.entities.FixedDepositRate;
import com.banking.ank.repostiories.FixedDepositRateRepository;

@Service
public class FixedDepositRateServiceImpl implements FixedDepositRateService {

	@Autowired
    private FixedDepositRateRepository fixedDepositRateRepository;

    public List<FixedDepositRate> getAllFixedDepositRates() {
        return fixedDepositRateRepository.findAll();
    }

    public FixedDepositRate getFixedDepositRateById(Long id) {
        return fixedDepositRateRepository.findById(id).orElse(null);
    }

    public void updateFixedDepositRate(Long id, FixedDepositRate updatedRate) {
        FixedDepositRate existingRate = fixedDepositRateRepository.findById(id).orElse(null);
        if (existingRate != null) {
            existingRate.setTimePeriodDays(updatedRate.getTimePeriodDays());
            existingRate.setTimePeriodMonths(updatedRate.getTimePeriodMonths());
            existingRate.setInterestRate(updatedRate.getInterestRate());
            fixedDepositRateRepository.save(existingRate);
        }
    }
    
}
