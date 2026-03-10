package com.mycontactsapp.exception;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String msg){
        super(msg);
    }
}