package com.example.exampleauth.controllers;


import com.example.exampleauth.dtos.DialogueInfoDto;
import com.example.exampleauth.dtos.MessageDto;
import com.example.exampleauth.enities.Message;
import com.example.exampleauth.services.MessageService;
import com.example.exampleauth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{userId}/messages")
public class MessageController {

    private final UserService userService;
    private final MessageService messageService;

    @GetMapping("/{otherUserId}")
    public List<MessageDto> getMessagesByUserId(@PathVariable("userId") Long userId, @PathVariable("otherUserId") Long otherUserId){
        return messageService.getMessageListByUserId(userId, otherUserId);
    }

    @GetMapping()
    public List<DialogueInfoDto> getDialogues(@PathVariable("userId") Long userId){
        return messageService.getDialoguesInfo(userId);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> addMessage(@RequestBody Message message){
         messageService.addMessage(message);
        return ResponseEntity.ok(HttpStatus.OK);

    }


}
