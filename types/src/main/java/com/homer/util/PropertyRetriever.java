package com.homer.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by arigolub on 1/26/15.
 */
public class PropertyRetriever {

    private static PropertyRetriever retriever;
    private static String env;

    private PropertyRetriever() {
        env = System.getProperty("env");
        if(env == null) {
            throw new RuntimeException("No env selected!");
        }
    }

    public static PropertyRetriever getInstance() {
        if(retriever == null) {
            retriever = new PropertyRetriever();
        }
        return retriever;
    }

    public String getProperty(String fileName, String propertyName) {
        String property = null;
        try {
            Properties p = new Properties();
            URL url = getPropFileStream(fileName);
            InputStream propFile = url.openStream();
            p.load(propFile);
            property = p.getProperty(propertyName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return property;
    }

    private URL getPropFileStream(String propertiesFileName) {
        int dot = propertiesFileName.lastIndexOf(".");
        String environmentalizedName = propertiesFileName.substring(0, dot) + "_" + env + "." + propertiesFileName.substring(dot + 1);
        URL url = PropertyRetriever.class.getClassLoader().getResource(environmentalizedName);
        if(url == null) {
            url = this.getClass().getResource(environmentalizedName);
        }

        if(url != null) {
            return url;
        } else {
            url = this.getClass().getResource(propertiesFileName);
            if(url == null) {
                url = PropertyRetriever.class.getClassLoader().getResource(propertiesFileName);
            }
        }
        return url;
    }

}
