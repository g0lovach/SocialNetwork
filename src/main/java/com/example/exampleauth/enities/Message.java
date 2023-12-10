package com.example.exampleauth.enities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "Messages")
@ToString
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long sender_id;

    @Column
    private Long receiver_id;

    @Column
    private String text;

    @Column
    Date sendingDate;

    @Column
    Date viewingDate;

    @ManyToOne(optional = false, targetEntity = User.class)
    @JoinColumn(name = "sender_id", referencedColumnName = "id", insertable=false, updatable=false )
    User sender;

    @ManyToOne(optional = false, targetEntity = User.class)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", insertable=false, updatable=false )
    User receiver;


    public Message() {

    }

    public Message(String text, User sender, User receiver, Date sendingDate, Date viewingDate) {
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
        this.receiver_id = receiver.getId();
        this.sender_id = sender.getId();
        this.sendingDate = sendingDate;
        this.viewingDate = viewingDate;
    }
}
