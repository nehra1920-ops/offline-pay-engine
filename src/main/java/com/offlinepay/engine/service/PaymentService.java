package com.offlinepay.engine.service;

import com.offlinepay.engine.Database.DBConnection;
import com.offlinepay.engine.Repository.AccountRepository;
import com.offlinepay.engine.Repository.LedgerEntryRepository;
import com.offlinepay.engine.Repository.PaymentRepository;
import com.offlinepay.engine.model.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Callable;

public class PaymentService {
    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    public PaymentService() {
        this.accountRepository = new AccountRepository();
        this.paymentRepository = new PaymentRepository();
        this.ledgerEntryRepository = new LedgerEntryRepository();
    }

    public void processPayment(Long senderAccountId, Long receiverAccountId, BigDecimal amount) {

        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);
            System.out.println("Payment transaction started.");

            Account sender  = accountRepository.findId(connection, senderAccountId);
            Account receiver = accountRepository.findId(connection, receiverAccountId);
            if(sender == null){
                throw  new IllegalArgumentException(" Sender Account do not found");

            }
            if(receiver == null){
                throw  new IllegalArgumentException(" Receiver Account do not found ");
            }
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(
                        "Payment amount must be greater than zero"
                );
            }

            if (senderAccountId.equals(receiverAccountId)) {
                throw new IllegalArgumentException(
                        "Sender and receiver accounts must be different"
                );
            }
            if(!sender.hasSufficientBankBalance(amount)){
                throw  new IllegalArgumentException("Insufficient BankBalance");
            }
            sender.withDraw(amount);
            accountRepository.updateBalance(connection , sender.getId(), sender.getBalance());
            receiver.deposit(amount);
            accountRepository.updateBalance(connection, receiver.getId(), receiver.getBalance());
            Payment payment = new Payment(null, sender.getId(), receiver.getId(), amount, PaymentStatus.COMPLETED, null);
            Long paymentId = paymentRepository.save(connection, payment);
            LedgerEntry debitEntry = new LedgerEntry(null, paymentId, sender.getId(), LedgerEntryType.DEBIT, amount, null);
            ledgerEntryRepository.save(connection, debitEntry);

            LedgerEntry creditEntry = new LedgerEntry(null, paymentId, receiver.getId(), LedgerEntryType.CREDIT, amount, null);
            ledgerEntryRepository.save(connection, creditEntry);
            connection.commit();
            System.out.println("Payment transaction completed.");


        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollBackException) {
                    rollBackException.printStackTrace();
                }
            }
            throw new RuntimeException("Payment failed", e);

        } finally {

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException closeException) {
                    closeException.printStackTrace();
                }
            }
        }


    }
}
