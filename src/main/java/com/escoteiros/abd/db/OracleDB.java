package com.escoteiros.abd.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDB {

    public static Connection connectDB(Connection conn) {
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe","abd","abd");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnDB(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String testConnection() {
        // Database connection
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe","abd","abd");
            conn.close();
        } catch (SQLException e) {
            return e.toString();
        }
        return "Conexão bem sucedida!";
    }
}
