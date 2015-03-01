package com.homer.exception;

/**
 * Created by arigolub on 2/28/15.
 */
public class NotFoundException extends Exception {

    private Class clazz;
    private Object key;

    public NotFoundException(Class clazz) {
        super();
        this.clazz = clazz;
    }

    public NotFoundException(Class clazz, Object key) {
        super();
        this.clazz = clazz;
        this.key = key;
    }

    @Override
    public String getMessage() {
        String message = "Could not find object for clazz " + clazz;
        if(key != null) {
            message += " wit key " + key;
        }
        return message;
    }
}
