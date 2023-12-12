package com.example.exampleauth.controllers;


import com.example.exampleauth.dtos.DialogueInfoDto;
import com.example.exampleauth.dtos.MessageDto;
import com.example.exampleauth.enities.Message;
import com.example.exampleauth.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/messages")
public class MessageController {

    private final MessageService messageService;


    @GetMapping("/{companionId}")
    public List<MessageDto> getMessagesByCompanionId( @PathVariable("companionId") Long companionId) {
        return messageService.getMessageListByUserId(companionId);
    }

    @GetMapping()
    public List<DialogueInfoDto> getDialogues(){
        return messageService.getDialoguesInfo();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> addMessage(@RequestBody Message message){
         messageService.addMessage(message);
        return ResponseEntity.ok(HttpStatus.OK);

    }


}
