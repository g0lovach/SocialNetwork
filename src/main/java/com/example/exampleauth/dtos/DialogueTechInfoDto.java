package com.example.exampleauth.dtos;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DialogueTechInfoDto {
    private Long senderId;
    private Long receiverId;
    private Long lastMessageId;

    public DialogueTechInfoDto(Long senderId, Long receiverId, Long lastMessageId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.lastMessageId = lastMessageId;
    }

}
