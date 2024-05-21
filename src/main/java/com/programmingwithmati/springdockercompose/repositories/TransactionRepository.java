package com.programmingwithmati.springdockercompose.repositories;

import com.programmingwithmati.springdockercompose.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

  List<Transaction> findByAccountNumber(String accountNumber);
}
