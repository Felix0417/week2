package com.aston_intensive.week2.db;

import com.aston_intensive.week2.PostgreSQLContainersTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PostgreSQLConnectionManagerTest extends PostgreSQLContainersTest {

    @Mock
    private PostgreSQLConnectionManager connectionManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getConnectionTest() throws SQLException {
        Connection connection = getConnection();
        when(connectionManager.getConnection()).thenReturn(connection);

        assertEquals(connection, connectionManager.getConnection());
    }
}