package com.offlinepay.engine.Repository;

import com.offlinepay.engine.model.Payment;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.stream.Collectors;
import java.sql.*;

public class PaymentRepository {
    public Long save(Connection connection, Payment payment) {
        String sql = """
                INSERT INTO payments(sender_account_id, receiver_account_id, amount, status)VALUES(?,?,?,?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, payment.getSender_account_id());
            statement.setLong(2, payment.getReceiverAccountId());
            statement.setBigDecimal(3, payment.getAmount());
            statement.setString(4, payment.getStatus().name());

            statement.executeUpdate();


            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
            throw new SQLException("Failed to get generated payment ID");
        } catch (SQLException e) {
            throw new RuntimeException("Error saving payment" + e);
        }

    }

}