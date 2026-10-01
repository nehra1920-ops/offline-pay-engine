package com.offlinepay.engine.Repository;

import com.offlinepay.engine.Database.DBConnection;
import com.offlinepay.engine.model.Account;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository {

    public Account findId(Connection connection, Long id) {

        String sql = """
                SELECT id, user_id, balance, currency, created_at
                FROM accounts
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Account(
                            resultSet.getLong("id"),
                            resultSet.getLong("user_id"),
                            resultSet.getBigDecimal("balance"),
                            resultSet.getString("currency"),
                            resultSet.getTimestamp("created_at").toLocalDateTime()
                    );
                }

                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding account by id", e);
        }
    }

    public void updateBalance(Connection connection ,Long accountId, BigDecimal newBalance) {

        String sql = """
                UPDATE accounts
                SET balance = ?
                WHERE id = ?
                """;

        try (

                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setBigDecimal(1, newBalance);
            statement.setLong(2, accountId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating account balance", e);
        }
    }
}