package com.programmingwithmati.springdockercompose.repositories;

import com.programmingwithmati.springdockercompose.model.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, String> {
  // Custom query to find an account balance by account number
  Optional<AccountBalance> findByAccountNumber(String accountNumber);

  // Additional custom queries or methods can be defined here if needed
}
