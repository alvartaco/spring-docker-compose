package com.programmingwithmati.springdockercompose;

import com.programmingwithmati.springdockercompose.model.AccountBalance;
import com.programmingwithmati.springdockercompose.model.Transaction;
import com.programmingwithmati.springdockercompose.repositories.AccountBalanceRepository;
import com.programmingwithmati.springdockercompose.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaTransactionListener {

  private final TransactionRepository transactionRepository;
  private final AccountBalanceRepository accountBalanceRepository;

  @KafkaListener(topics = "transactions", groupId = "transaction-group", containerFactory = "kafkaListenerContainerFactory")
  @Transactional
  public void listen(Transaction transaction) {
    log.info("Transaction Received: {}", transaction);

    transactionRepository.save(transaction);

    log.info("Transaction saved");

    AccountBalance accountBalance = accountBalanceRepository.findByAccountNumber(transaction.getAccountNumber())
            .orElse(new AccountBalance(transaction.getAccountNumber(), BigDecimal.ZERO, transaction.getCurrency(), transaction.getTransactionDate()));

    log.info("Account Balance found: {}", accountBalance);

    var newBalance = accountBalance.processTransaction(transaction);

    accountBalanceRepository.save(newBalance);

    log.info("Account Balance Updated");

  }
}
