package com.example.exampleauth.enities;


import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "Friendship")
public class Friendship {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    Long user_id1;
    @Column
    Long user_id2;


    @ManyToOne(optional = false, targetEntity = User.class)
    @JoinColumn(name = "user_id1", referencedColumnName = "id", insertable=false, updatable=false )
    User userOne;

    @ManyToOne(optional = false, targetEntity = User.class)
    @JoinColumn(name = "user_id2", referencedColumnName = "id", insertable=false, updatable=false )
    User userTwo;

    public Friendship(User userOne, User userTwo) {
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.user_id1 = userOne.getId();
        this.user_id2 = userTwo.getId();
    }

    public Friendship() {

    }

    public void setUserOne(User userOne) {
        this.userOne = userOne;
    }

    public void setUserTwo(User userTwo) {
        this.userTwo = userTwo;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
