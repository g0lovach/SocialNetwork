package com.example.exampleauth.config.exceptions;

public class UserNotExist extends RuntimeException{
    public UserNotExist(String message){
        super(message);
    }
}
