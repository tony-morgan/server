package com.example.security.exceptions;

/**
 * Created by tonym on 20.04.2017.
 */
public class EmailExistsException extends Exception {

    public EmailExistsException(String message){
        super(message);
    }
}
