package com.blogspot.newbie.chatroom.dao;

import com.blogspot.newbie.chatroom.model.ServerModel;
import java.util.List;

/**
 *
 * @author agus
 */
public interface ServerDao {
    public void save(ServerModel dataServer);

    public void update(ServerModel dataServer);

    public void delete(ServerModel dataServer);

    public ServerModel getServer(String id_chat);

    public List<ServerModel> getDataChatServers();
}
