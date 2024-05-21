package com.programmingwithmati.springdockercompose;

import com.programmingwithmati.springdockercompose.model.AccountBalance;
import com.programmingwithmati.springdockercompose.model.Transaction;
import com.programmingwithmati.springdockercompose.repositories.AccountBalanceRepository;
import com.programmingwithmati.springdockercompose.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

  private final AccountBalanceRepository accountBalanceRepository;
  private final TransactionRepository transactionRepository;


  @GetMapping("/{accountNumber}/balance")
  public AccountBalance getAccountBalance(@PathVariable String accountNumber) {
    return accountBalanceRepository.findByAccountNumber(accountNumber)
            .orElseThrow();
  }

  @GetMapping("/{accountNumber}/transactions")
  public List<Transaction> getTransactions(@PathVariable String accountNumber) {
    return transactionRepository.findByAccountNumber(accountNumber);
  }
}
