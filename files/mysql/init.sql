CREATE SCHEMA bank;
USE bank;

CREATE TABLE transactions (
                              id VARCHAR(255) NOT NULL PRIMARY KEY,
                              account_number VARCHAR(20) NOT NULL,
                              amount DECIMAL(15, 2) NOT NULL, -- Assuming a maximum of 13 digits before the decimal and 2 after
                              currency VARCHAR(3) NOT NULL,
                              type VARCHAR(50) NOT NULL,
                              status VARCHAR(50) NOT NULL,
                              transaction_date TIMESTAMP NOT NULL
);

CREATE TABLE account_balance (
                                 account_number VARCHAR(20) NOT NULL PRIMARY KEY,
                                 balance DECIMAL(15, 2) NOT NULL,
                                 currency VARCHAR(3) NOT NULL,
                                 last_updated TIMESTAMP NOT NULL
);

INSERT INTO bank.account_balance (account_number, balance, currency, last_updated) VALUES ('123456789', 1000.00, 'GBP', '2024-05-21 15:19:17');
