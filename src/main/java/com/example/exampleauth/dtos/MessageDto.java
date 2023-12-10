package com.example.exampleauth.dtos;

import com.example.exampleauth.enities.Message;
import com.example.exampleauth.enities.User;
import lombok.Getter;

import java.sql.Date;


@Getter
public class MessageDto {
    private Date sendingDate;

    private Date viewingDate;

    private String text;

    public MessageDto(Message message){
        this.text = message.getText();
        this.sendingDate = message.getSendingDate();
        this.viewingDate = message.getViewingDate();
    }
}
