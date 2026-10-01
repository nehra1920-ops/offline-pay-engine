package com.offlinepay.engine.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {


        private Long id;
        private Long userId;
        private BigDecimal balance;
        private String currency;
        private LocalDateTime createdAt;

        public Account(Long id, Long userId, BigDecimal balance, String currency, LocalDateTime createdAt) {
                this.id = id;
                this.userId = userId;
                this.balance = balance;
                this.currency = currency;
                this.createdAt = createdAt;
        }
        public Account() {
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Long getUserId() {
                return userId;
        }

        public void setUserId(Long userId) {
                this.userId = userId;
        }

        public BigDecimal getBalance() {
                return balance;
        }

        public void setBalance(BigDecimal balance) {
                this.balance = balance;
        }

        public String getCurrency() {
                return currency;
        }

        public void setCurrency(String currency) {
                this.currency = currency;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }
        public void deposit(BigDecimal amount){
                if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0){
                        throw new IllegalArgumentException("Deposit amount must e greater than zero");
                }
                balance =balance.add(amount);
        }
        public  void withDraw(BigDecimal amount){
                if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0){
                        throw new IllegalArgumentException("WithDrawl amount must e greater than zero");
                }
                if(!hasSufficientBankBalance(amount)){
                        throw  new IllegalArgumentException("Insufficient BankBalance");
                }
                balance =balance.subtract(amount);

        }
        public boolean hasSufficientBankBalance(BigDecimal amount){
                return balance.compareTo(amount) >0;
        }
}
