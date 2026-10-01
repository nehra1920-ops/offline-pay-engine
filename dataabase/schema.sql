USE offline_pay_engine;
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,

                       name VARCHAR(100) NOT NULL,

                       email VARCHAR(255) NOT NULL UNIQUE,

                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE accounts (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,

                          user_id BIGINT NOT NULL,

                          balance DECIMAL(19,2) NOT NULL DEFAULT 0.00,

                          currency VARCHAR(3) NOT NULL DEFAULT 'INR',

                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT fk_accounts_user
                              FOREIGN KEY (user_id)
                                  REFERENCES users(id),

                          CONSTRAINT chk_account_balance
                              CHECK (balance >= 0)
);


CREATE TABLE payments (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,

                          sender_account_id BIGINT NOT NULL,

                          receiver_account_id BIGINT NOT NULL,

                          amount DECIMAL(19,2) NOT NULL,

                          status VARCHAR(30) NOT NULL,

                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT fk_payment_sender
                              FOREIGN KEY (sender_account_id)
                                  REFERENCES accounts(id),

                          CONSTRAINT fk_payment_receiver
                              FOREIGN KEY (receiver_account_id)
                                  REFERENCES accounts(id),

                          CONSTRAINT chk_payment_amount
                              CHECK (amount > 0),

                          CONSTRAINT chk_payment_different_accounts
                              CHECK (sender_account_id <> receiver_account_id)
);


CREATE TABLE ledger_entries (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,

                                payment_id BIGINT NOT NULL,

                                account_id BIGINT NOT NULL,

                                entry_type VARCHAR(10) NOT NULL,

                                amount DECIMAL(19,2) NOT NULL,

                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                CONSTRAINT fk_ledger_payment
                                    FOREIGN KEY (payment_id)
                                        REFERENCES payments(id),

                                CONSTRAINT fk_ledger_account
                                    FOREIGN KEY (account_id)
                                        REFERENCES accounts(id),

                                CONSTRAINT chk_ledger_amount
                                    CHECK (amount > 0),

                                CONSTRAINT chk_ledger_type
                                    CHECK (entry_type IN ('DEBIT', 'CREDIT'))
);