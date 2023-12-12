package com.example.exampleauth.services;


import com.example.exampleauth.dtos.DialogueInfoDto;
import com.example.exampleauth.dtos.DialogueTechInfoDto;
import com.example.exampleauth.dtos.MessageDto;
import com.example.exampleauth.enities.Message;
import com.example.exampleauth.repositories.MessageRepository;
import com.example.exampleauth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MessageService {



    private final UserRepository userRepository;

    private final MessageRepository messageRepository;


    @Autowired
    public MessageService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public List<MessageDto> getMessageListByUserId(Long companionId){
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        List<Message> messages = messageRepository.findMessageBySender_idAndReceiver_idOrSender_idAndReceiver_id(
                userId, companionId, companionId, userId);
        List<MessageDto> messageDtos = new ArrayList<>();
        messages.forEach(message -> messageDtos.add(new MessageDto(message)));
        return messageDtos;
    }

    public List<DialogueInfoDto> getDialoguesInfo() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();

        List<DialogueTechInfoDto> dialogueTechInfoDtos = messageRepository.
                findDialogues(userId);
        Map<Long, Long> companionAndLastMessageId = new HashMap<>();
        List<Message> messages = new ArrayList<>();
        List<DialogueInfoDto> dialogueInfoDtos = new ArrayList<>();
        for (DialogueTechInfoDto dialogueTechInfoDto : dialogueTechInfoDtos) {
            if (dialogueTechInfoDto.getSenderId() != userId) {
                if (companionAndLastMessageId.get(dialogueTechInfoDto.getSenderId()) == null
                        || companionAndLastMessageId.get(dialogueTechInfoDto.getSenderId()) < dialogueTechInfoDto.getLastMessageId()) {
                    companionAndLastMessageId.put(dialogueTechInfoDto.getSenderId(), dialogueTechInfoDto.getLastMessageId());
                }

            } else {
                if (companionAndLastMessageId.get(dialogueTechInfoDto.getReceiverId()) == null
                        || companionAndLastMessageId.get(dialogueTechInfoDto.getReceiverId()) < dialogueTechInfoDto.getLastMessageId()) {
                    companionAndLastMessageId.put(dialogueTechInfoDto.getReceiverId(), dialogueTechInfoDto.getLastMessageId());
                }
            }
        }
        companionAndLastMessageId.values().forEach(item->messages.add(messageRepository.findById(item).get()));
        for (Message message: messages){
            if (message.getSender_id()!=userId){
                dialogueInfoDtos.add(new DialogueInfoDto(
                        message.getSender_id(),
                        message.getSender().getUsername(),
                        message.getSendingDate(),
                        message.getText()));
            }
            else{
                dialogueInfoDtos.add(new DialogueInfoDto(
                        message.getReceiver_id(),
                        message.getReceiver().getUsername(),
                        message.getSendingDate(),
                        message.getText()));
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







