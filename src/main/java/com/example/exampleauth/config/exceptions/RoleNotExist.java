package com.example.exampleauth.config.exceptions;

public class RoleNotExist extends RuntimeException{
    public RoleNotExist(String message){
        super(message);
    }
}
