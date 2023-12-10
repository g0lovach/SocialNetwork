package com.example.exampleauth.services;


import com.example.exampleauth.dtos.DialogueInfoDto;
import com.example.exampleauth.dtos.DialogueTechInfoDto;
import com.example.exampleauth.dtos.MessageDto;
import com.example.exampleauth.enities.Message;
import com.example.exampleauth.repositories.MessageRepository;
import com.example.exampleauth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {



    private  UserRepository userRepository;

    private MessageRepository messageRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Autowired
    public void setMessageRepository(MessageRepository messageRepository){this.messageRepository = messageRepository;}

    public List<MessageDto> getMessageListByUserId(Long userId, Long otherUserId){
        List<Message> messages = messageRepository.findMessageBySender_idAndReceiver_idOrSender_idAndReceiver_id(
                userId, otherUserId, otherUserId, userId);
        List<MessageDto> messageDtos = new ArrayList<>();
        messages.forEach(message -> messageDtos.add(new MessageDto(message)));
        return messageDtos;
    }

    public List<DialogueInfoDto> getDialoguesInfo(Long userId) {
        List<DialogueTechInfoDto> dialogueTechInfoDtos = messageRepository.findDialogues(userId);
        Map<Long, Long> otherUserAndLastMessageId = new HashMap<>();
        List<Message> messages = new ArrayList<>();
        List<DialogueInfoDto> dialogueInfoDtos = new ArrayList<>();
        for (DialogueTechInfoDto dialogueTechInfoDto : dialogueTechInfoDtos) {
            if (dialogueTechInfoDto.getSenderId() != userId) {
                if (otherUserAndLastMessageId.get(dialogueTechInfoDto.getSenderId()) == null
                        || otherUserAndLastMessageId.get(dialogueTechInfoDto.getSenderId()) < dialogueTechInfoDto.getLastMessageId()) {
                    otherUserAndLastMessageId.put(dialogueTechInfoDto.getSenderId(), dialogueTechInfoDto.getLastMessageId());
                }

            } else {
                if (otherUserAndLastMessageId.get(dialogueTechInfoDto.getReceiverId()) == null
                        || otherUserAndLastMessageId.get(dialogueTechInfoDto.getReceiverId()) < dialogueTechInfoDto.getLastMessageId()) {
                    otherUserAndLastMessageId.put(dialogueTechInfoDto.getReceiverId(), dialogueTechInfoDto.getLastMessageId());
                }
            }
        }
        for (Long item: otherUserAndLastMessageId.values()){
            messages.add(messageRepository.findById(item).get());
        }
        for (Message message: messages){
            if (message.getSender_id()!=userId){
                dialogueInfoDtos.add(new DialogueInfoDto(
                        message.getSender().getUsername(), message.getSendingDate(), message.getText()));
            }
            else{
                dialogueInfoDtos.add(new DialogueInfoDto(
                        message.getReceiver().getUsername(), message.getSendingDate(), message.getText()));
            }
        }
        return dialogueInfoDtos;

    }

    @Transactional
    public void addMessage(Message message){
        if (userRepository.existsById(message.getSender_id()) && userRepository.existsById(message.getReceiver_id())){
            messageRepository.save(message);
        }
    }





    }







