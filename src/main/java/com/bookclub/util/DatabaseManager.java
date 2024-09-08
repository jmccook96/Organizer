package com.bookclub.util;

import com.sun.source.tree.InstanceOfTree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

public class DatabaseManager {
    // Singleton implementation.
    private static Connection instance = null;
    private static final String URL = "jdbc:sqlite:bookclub.db";
    
    private DatabaseManager() {
        try {
            instance = DriverManager.getConnection(URL);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }
    
    public static Connection getInstance() {
        if (instance == null) {
            new DatabaseManager(); // I personally hate this approach.
        }
        return instance;
    }
}
