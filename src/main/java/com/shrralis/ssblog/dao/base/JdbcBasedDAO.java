package com.shrralis.ssblog.dao.base;

import com.shrralis.ssblog.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcBasedDAO {
    private Connection connection;

    protected JdbcBasedDAO() throws ClassNotFoundException, SQLException {
        Class.forName(DatabaseConfig.DRIVER_CLASS_NAME);

        connection = DriverManager.getConnection(
                DatabaseConfig.JDBC_PROTOCOL + "://" + DatabaseConfig.HOST + ":" +
                        DatabaseConfig.PORT + "/" + DatabaseConfig.DB_NAME,
                DatabaseConfig.USER, DatabaseConfig.PASS);
    }

    protected Connection getConnection() {
        return connection;
    }
}
