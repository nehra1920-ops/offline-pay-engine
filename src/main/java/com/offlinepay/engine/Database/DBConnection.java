package com.offlinepay.engine.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/offline_pay_engine";
    private  static final   String USERNAME  = "root";
    private static final String PASSWORD = System.getenv("MYSQL_PASSWORD");;

    public  static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);  //DRIVERS LOADED

    }

}
