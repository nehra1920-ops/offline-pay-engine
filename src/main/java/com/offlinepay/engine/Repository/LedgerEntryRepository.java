package com.offlinepay.engine.Repository;

import com.offlinepay.engine.model.LedgerEntry;

import java.sql.*;

public class LedgerEntryRepository {
    public  void save(Connection connection, LedgerEntry ledgerEntry){
        String sql = """
                INSERT INTO ledger_entries(payment_id, account_id, entry_type, amount)VALUES(?,?,?,?)
                """;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, ledgerEntry.getPaymentId());
            statement.setLong(2,ledgerEntry.getAccountId());
            statement.setString(3, ledgerEntry.getEntryType().name());
            statement.setBigDecimal(4, ledgerEntry.getAmount());
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException("Error saving ledger entry", e);
        }
    }


}
