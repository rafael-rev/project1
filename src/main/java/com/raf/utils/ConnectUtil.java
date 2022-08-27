package com.raf.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ConnectUtil {
    // private static final String url = "jdbc:postgresql://sandbox.cprjerwhpjzg.us-east-1.rds.amazonaws.com:5432/project1";
    // private static final String user = "rafael";
    // private static final String password = "postgres";
    private static final String url = "jdbc:postgresql://localhost:11000/project1";
    private static final String user = "raf";
    private static final String password = "nnn123";

    static Logger logger = LogManager.getLogger(ConnectUtil.class);

    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.error("FATAL: DB Connect Error - ConnectUtil --", e);
        }
        return conn;
    }
}
