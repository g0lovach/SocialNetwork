package com.example.exampleauth.dtos;


import lombok.Getter;

import java.sql.Date;

@Getter
public class DialogueInfoDto {
    private Long id;
    private String username;
    private Date sendingDate;
    private String text;

    public DialogueInfoDto(Long id, String username, Date sendingDate, String text){
        this.id = id;
        this.sendingDate = sendingDate;
        this.text = text;
        this.username = username;
    }
}
