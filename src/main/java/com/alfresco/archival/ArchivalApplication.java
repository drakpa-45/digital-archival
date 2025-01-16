package com.alfresco.archival;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class ArchivalApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;  // Autowiring the JdbcTemplate

    public static void main(String[] args) {
        SpringApplication.run(ArchivalApplication.class, args);
        // No need to manually call checkDatabaseVersion here
    }

    @Override
    public void run(String... args) throws Exception {
        checkDatabaseVersion();  // This will be called after the application context is fully initialized
    }

    // Method to check the database version
    private void checkDatabaseVersion() {
        String sql = "SELECT version();";
        String version = jdbcTemplate.queryForObject(sql, String.class);
        System.out.println("PostgreSQL version: " + version);
    }
}

