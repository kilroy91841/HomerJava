package com.homer.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by arigolub on 2/3/15.
 */
public class Factory {

    private static final String FACTORY_FILE = "factory-config.properties";

    public static <T> T getImplementation(Class<T> classToImplement) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String implementation = PropertyRetriever.getInstance().getProperty(FACTORY_FILE, classToImplement.getName());
        Class<?> clazz = Class.forName(implementation);
        Constructor<?> ctor = clazz.getConstructor();
        Object object = ctor.newInstance();
        return (T)object;
    }
}
