package com.banking.ank.repostiories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.ank.entities.FixedDeposit;

@Repository
public interface FixedDepositRepository extends JpaRepository<FixedDeposit,Long>{

	List<FixedDeposit> getByaccountId(String account);

}
