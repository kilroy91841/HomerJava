package com.homer.dao;

import com.homer.util.PropertyRetriever;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

/**
 * Created by arigolub on 1/26/15.
 */
public class MySQLDAO {

    private static String env;
    private String connectionString;
    private String username;
    private String password;

    private static String propertyFile = "database.properties";
    private static String connectionStringProperty = "connectionString";
    private static String usernameProperty = "username";
    private static String passwordProperty = "password";

    protected MySQLDAO() {
        connectionString = PropertyRetriever.getInstance().getProperty(propertyFile, connectionStringProperty);
        username = PropertyRetriever.getInstance().getProperty(propertyFile, usernameProperty);
        password = PropertyRetriever.getInstance().getProperty(propertyFile, passwordProperty);
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(connectionString, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeAll(ResultSet rs, Statement statement, Connection connection) throws SQLException {
        rs.close();
        statement.close();
        connection.close();
    }

    public static class FACTORY {

        private static MySQLDAO instance;

        public static MySQLDAO getInstance() {
            if(instance == null) {
                instance = new MySQLDAO();
            }
            return instance;
        }
    }

}
