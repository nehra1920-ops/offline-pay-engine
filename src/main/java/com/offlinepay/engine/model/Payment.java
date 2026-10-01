package com.offlinepay.engine.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private Long id;
    private  Long sender_account_id;
    private Long receiverAccountId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime createdAt;

    public Payment(Long id, Long sender_account_id, Long receiverAccountId, BigDecimal amount, PaymentStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.sender_account_id = sender_account_id;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSender_account_id() {
        return sender_account_id;
    }

    public void setSender_account_id(Long sender_account_id) {
        this.sender_account_id = sender_account_id;
    }

    public Long getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(Long receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
