package com.banking.ank.repostiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.ank.entities.FixedDepositRate;


@Repository
public interface FixedDepositRateRepository extends JpaRepository<FixedDepositRate,Long> {

}
