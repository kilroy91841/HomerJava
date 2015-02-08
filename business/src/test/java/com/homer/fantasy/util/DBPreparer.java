package com.homer.fantasy.util;

import com.homer.util.PropertyRetriever;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;

/**
 * Created by arigolub on 2/8/15.
 */
public class DBPreparer {

    private static String propertyFile = "database.properties";
    private static String connectionStringProperty = "connectionString";
    private static String usernameProperty = "username";
    private static String passwordProperty = "password";

    public static DriverManagerDestination getDriverManagerDestination() {
        String dbURL = PropertyRetriever.getInstance().getProperty(propertyFile, connectionStringProperty);
        String dbUser = PropertyRetriever.getInstance().getProperty(propertyFile, usernameProperty);
        String dbPass = PropertyRetriever.getInstance().getProperty(propertyFile, passwordProperty);

        return new DriverManagerDestination(dbURL, dbUser, dbPass);
    }
}
