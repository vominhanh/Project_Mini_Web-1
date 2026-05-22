package com.training.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {

    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.trim().isEmpty()) ? value : defaultValue;
    }

    public static Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");

        String url = getEnv("USER_STORAGE_JDBC_URL", "jdbc:postgresql://host.docker.internal:5432/training_auth");
        String user = getEnv("USER_STORAGE_JDBC_USER", "postgres");
        String password = getEnv("USER_STORAGE_JDBC_PASSWORD", "123321");

        return DriverManager.getConnection(url, user, password);
    }
}