package com.example.exampleauth.repositories;

import com.example.exampleauth.dtos.DialogueTechInfoDto;
import com.example.exampleauth.enities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findMessageBySender_idAndReceiver_idOrSender_idAndReceiver_id(
            Long sender_id, Long receiver_id, Long receiver_id2, Long sender_id2);
    @Query(value = "select new com.example.exampleauth.dtos.DialogueTechInfoDto( m.sender_id,m.receiver_id, max(m.id)) " +
            "from Message m where m.sender_id = :userId or m.receiver_id = :userId group by m.sender_id, m.receiver_id")
    List<DialogueTechInfoDto> findDialogues(Long userId);


}
