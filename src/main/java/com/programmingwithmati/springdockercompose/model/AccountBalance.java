package com.programmingwithmati.springdockercompose.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_balance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AccountBalance {

  @Id
  @Column(name = "account_number", nullable = false)
  private String accountNumber;

  @Column(name = "balance", nullable = false, precision = 15, scale = 2)
  private BigDecimal balance;

  @Column(name = "currency", nullable = false, length = 3)
  private String currency;

  @Column(name = "last_updated", nullable = false)
  private LocalDateTime lastUpdated;

  public AccountBalance processTransaction(Transaction transaction) {
    var newBalance = this.toBuilder();

    if ("DEPOSIT".equals(transaction.getType()) || "TRANSFER".equals(transaction.getType())) {
      newBalance.balance(this.getBalance().add(transaction.getAmount()));
    } else if ("WITHDRAWAL".equals(transaction.getType())) {
      newBalance.balance(this.getBalance().subtract(transaction.getAmount()));
    }
    newBalance.lastUpdated(transaction.getTransactionDate());

    return newBalance.build();
  }
}
