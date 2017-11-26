package com.shrralis.ssblog.config;

public class DatabaseConfig {
    public static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    public static final String JDBC_PROTOCOL = "jdbc:postgresql";
    public static final String HOST = "localhost";
    public static final String PORT = "5432";
    public static final String DB_NAME = "ss_blog";
    public static final String USER = "shrralis";
    public static final String PASS = "";

    private DatabaseConfig() {
    }
}
