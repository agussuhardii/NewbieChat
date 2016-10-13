package com.blogspot.newbie.chatroom.dao;

import com.blogspot.newbie.chatroom.model.ClientModel;
import java.util.List;

/**
 *
 * @author agus suhardi<agus.suhardii@gmail.com>
 */
public interface ClientDao {

    public ClientModel getKode(int kode);

    public List<ClientModel> getClient();

    public void save(ClientModel m);

    public void update(ClientModel m);

    public void delete(ClientModel m);
}
