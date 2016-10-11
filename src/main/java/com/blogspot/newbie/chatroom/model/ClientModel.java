package com.blogspot.newbie.chatroom.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class ClientModel implements Serializable{
    
    
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "kode")
    int kode;
    
    @Column(name = "nama")
    String nama;
    
    @Column(name = "keterangan")
    String keterangan;
    /*
    @Column(name = "msg_read")
    boolean msgRead;

    public boolean isMsgRead() {
        return msgRead;
    }

    public void setMsgRead(boolean msgRead) {
        this.msgRead = msgRead;
    }
    
    */
    

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    
    
    
    
    
}
