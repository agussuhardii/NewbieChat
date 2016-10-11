package com.blogspot.newbie.chatroom.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserModel implements Serializable{
    
    @Id
    @Column(name = "user_name")
    private String userName;
    
    private String password;
}
