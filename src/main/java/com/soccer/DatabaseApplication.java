package com.soccer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class DatabaseApplication implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("DataSource: " + dataSource);
        try {
            dataSource.getConnection().isValid(5);
            System.out.println("Database connection is valid!");
        } catch (Exception e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
    }
}
