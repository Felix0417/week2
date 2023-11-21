package com.aston_intensive.week2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
public class PostgreSQLContainersTest {
    @Container
    protected static final PostgreSQLContainer<?> dbContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("week2")
                    .withUsername("user")
                    .withPassword("password");


    @BeforeAll
    static void beforeAll() {
        dbContainer.start();

        try (Connection connection = DriverManager.getConnection(dbContainer.getJdbcUrl(), dbContainer.getUsername(), dbContainer.getPassword());
             Statement statement = connection.createStatement()) {
            String sqlScript = readScript("src/test/java/resources/init-postgres.sql");
            statement.executeUpdate(sqlScript);

            System.out.println("База данных успешно заполнена.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void afterAll() {
        dbContainer.stop();
    }


    @Test
    void testCurrentDateNotNull() throws Exception {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT CURRENT_DATE");
            assertNotNull(resultSet);
            resultSet.next();
            LocalDate currentDate = resultSet.getObject(1, LocalDate.class);
            System.out.println("Date is " + currentDate);
            assertNotNull(currentDate);
        }
    }

    private static String readScript(String scriptPath) {
        StringBuilder script = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(scriptPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return script.toString();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbContainer.getJdbcUrl(), dbContainer.getUsername(), dbContainer.getPassword());
    }
}
