package com.aston_intensive.week2;

import com.aston_intensive.week2.db.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnectionManager implements ConnectionManager {
    @Override
    public Connection getConnection() throws SQLException {
        return PostgreSQLContainersTest.getConnection();
    }
}
