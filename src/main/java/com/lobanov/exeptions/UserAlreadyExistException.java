package com.lobanov.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(String msg) {
        super(msg);
    }
}
