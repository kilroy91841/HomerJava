package com.homer.exception;

/**
 * Created by arigolub on 2/21/15.
 */
public class DisallowedTransactionException extends Exception {

    public DisallowedTransactionException(String message) {
        super(message);
    }
}
