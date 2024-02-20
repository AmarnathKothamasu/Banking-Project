package com.banking.ank.repostiories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.ank.entities.TransactionDetails;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails,Long>{

	List<TransactionDetails> findBytransferFrom(String accountNumber);
}
