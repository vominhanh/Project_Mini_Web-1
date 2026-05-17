package com.training.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {

    private static final String URL =
            "jdbc:postgresql://host.docker.internal:5432/training_auth";

    private static final String USER =
            "training_owner";

    private static final String PASSWORD =
            "123456";

    public static Connection
    getConnection()
    throws Exception {

        Class.forName(
                "org.postgresql.Driver"
        );

        return DriverManager.getConnection(

                URL,

                USER,

                PASSWORD
        );
    }
}