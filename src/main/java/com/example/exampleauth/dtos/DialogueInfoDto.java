package com.example.exampleauth.dtos;


import com.example.exampleauth.enities.Message;
import lombok.Getter;

import java.sql.Date;

@Getter
public class DialogueInfoDto {
    private String username;
    private Date sendingDate;
    private String text;

    public DialogueInfoDto(String username, Date sendingDate, String text){
        this.sendingDate = sendingDate;
        this.text = text;
        this.username = username;
    }
}
