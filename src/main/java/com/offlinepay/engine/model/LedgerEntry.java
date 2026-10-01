package com.offlinepay.engine.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LedgerEntry {


        private Long id;
        private Long paymentId;
        private Long accountId;
        private LedgerEntryType entryType;
        private BigDecimal amount;
        private LocalDateTime createdAt;

        public LedgerEntry(Long id, Long paymentId, Long accountId, LedgerEntryType entryType, BigDecimal amount, LocalDateTime createdAt) {
                this.id = id;
                this.paymentId = paymentId;
                this.accountId = accountId;
                this.entryType = entryType;
                this.amount = amount;
                this.createdAt = createdAt;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Long getPaymentId() {
                return paymentId;
        }

        public void setPaymentId(Long paymentId) {
                this.paymentId = paymentId;
        }

        public Long getAccountId() {
                return accountId;
        }

        public void setAccountId(Long accountId) {
                this.accountId = accountId;
        }

        public LedgerEntryType getEntryType() {
                return entryType;
        }

        public void setEntryType(LedgerEntryType entryType) {
                this.entryType = entryType;
        }

        public BigDecimal getAmount() {
                return amount;
        }

        public void setAmount(BigDecimal amount) {
                this.amount = amount;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }
}
