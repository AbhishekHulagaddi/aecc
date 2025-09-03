package com.rim.transactions.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import com.rim.transactions.domain.Transactions;

public interface TransactionsRepo extends JpaRepository<Transactions, UUID>,JpaSpecificationExecutor<Transactions>{

}
