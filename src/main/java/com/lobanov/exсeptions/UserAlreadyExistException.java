package com.lobanov.exсeptions;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(String msg) {
        super(msg);
    }
}
