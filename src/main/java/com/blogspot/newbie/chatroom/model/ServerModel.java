package com.blogspot.newbie.chatroom.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author agus
 */
@Entity
@Table(name = "server")
public class ServerModel implements Serializable{
    @Id
    //@GenericGenerator(name = "uuid2", strategy = "uuid2")
    //@GeneratedValue(generator = "uuid2")
    @Column(name = "id_chat", length = 150)
    private String idChat;
     
    @Column(name = "user_chat", length = 20)
    private String userChat; 
    
    @Column(name = "messenger_chat", length = 150)
    private String msgChat; 
    
    @Column(name = "time_chat")//for postgresql, columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeChat; 

    @Column(name = "msg_type")
    private String msgType;
    
    
    @Column(name = "msg_read")
    boolean msgRead;

    public boolean isMsgRead() {
        return msgRead;
    }

    public void setMsgRead(boolean msgRead) {
        this.msgRead = msgRead;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public String getUserChat() {
        return userChat;
    }

    public void setUserChat(String userChat) {
        this.userChat = userChat;
    }

    public String getMsgChat() {
        return msgChat;
    }

    public void setMsgChat(String msgChat) {
        this.msgChat = msgChat;
    }

    public Date getTimeChat() {
        return timeChat;
    }

    public void setTimeChat(Date timeChat) {
        this.timeChat = timeChat;
    }


    
    
    
    
}
