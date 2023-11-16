package com.aston_intensive.week2.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLConnectionManager implements ConnectionManager {
    @Override
    public Connection getConnection() throws SQLException {
        try (FileInputStream fis = new FileInputStream("src/main/resources/db.properties")) {
            Properties prop = new Properties();
            prop.load(fis);

            var url = prop.getProperty("postgres.url");
            var user = prop.getProperty("postgres.user");
            var password = prop.getProperty("postgres.password");

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
