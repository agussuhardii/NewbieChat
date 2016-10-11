package com.blogspot.newbie.chatroom.dao;

import com.blogspot.newbie.chatroom.model.ClientModel;
import java.util.List;

public interface ClientDao {
    public ClientModel getKode(int kode);
    public List<ClientModel> getClient();
}
