package com.abel.hwes.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBUtil {
    /**
     * Get Collection Object
     * @return
     */
    public static Connection getConnection() {
        try {
            Class.forName(PropertiesUtil.getProperty("JDBC_DRIVER"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
//            throw new LoadException("Driver load fialed!");
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(PropertiesUtil.getProperty("JDBC_URL"), PropertiesUtil.getProperty("JDBC_USER"), PropertiesUtil.getProperty("JDBC_PASSWORD"));
        } catch (SQLException e) {
            e.printStackTrace();
//            throw new DBException("DB connection failed!");
        }
        return conn;
    }

    /**
     * Close the connection, release the resources.
     */
    public static void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if (rs != null){
                rs.close();
            }

            if (stmt != null){
                stmt.close();
            }

            if (conn != null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
//            throw new DBException("DB close failed!");
        }
    }

    public static void setAutoCommit(Connection conn, boolean autoCommit) {
        try {
            conn.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            e.printStackTrace();
//            throw new DBException("DB setAutoCommit failed!");
        }

    }

    public static void commit(Connection conn) {
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
//            throw new DBException("DB commit failed!");
        }

    }

    public static void rollback(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
//            throw new DBException("DB rollback failed!");
        }

    }
}
