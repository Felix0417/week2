package com.aston_intensive.week2.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLConnectionManager implements ConnectionManager {
    @Override
    public Connection getConnection() throws SQLException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new FileNotFoundException("Unable to find properties");
            }
            Class.forName("org.postgresql.Driver");
            Properties prop = new Properties();
            prop.load(input);

            var url = prop.getProperty("postgres.url");
            var user = prop.getProperty("postgres.user");
            var password = prop.getProperty("postgres.password");

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
