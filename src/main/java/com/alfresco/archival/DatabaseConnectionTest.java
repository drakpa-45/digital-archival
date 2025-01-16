package com.alfresco.archival;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

public class DatabaseConnectionTest {
    @Autowired
    private DataSource dataSource;

    @Bean
    public CommandLineRunner checkConnection() {
        return args -> {
            try (Connection connection = dataSource.getConnection()) {
                if (connection != null && !connection.isClosed()) {
                    System.out.println("Database connection successful!");
                } else {
                    System.out.println("Database connection failed!");
                }
            } catch (SQLException e) {
                System.err.println("Database connection failed: " + e.getMessage());
            }
        };
    }
}
