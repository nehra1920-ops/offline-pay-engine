package com.offlinepay.engine.Repository;

//import com.offlinepay.engine.database.DBConnection;
import com.offlinepay.engine.Database.DBConnection;
import com.offlinepay.engine.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRepository {

    public User findById(Long id){
        String sql  = """
            SELECT id,name,email,created_at  FROM users WHERE id = ?
            """;

        try (Connection connection =DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql) ){

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                return  new User(resultSet.getLong("id")
                        , resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getTimestamp("created_at").toLocalDateTime());

            }
            return null;


        }catch (SQLException e){
           throw  new RuntimeException("Error finding user by id ",e);

    }


}
}

