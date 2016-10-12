package com.blogspot.newbie.chatroom;

import com.blogspot.newbie.chatroom.dao.ServerDao;
import com.blogspot.newbie.chatroom.model.ServerModel;
import java.util.List;

public class NewClass {
    public static void main(String[] args) {
        NewClass c = new NewClass();
        c.get();
    }

    
      private final static ServerDao dao = HibernateUtil.getServerDao();
      public void get(){
          List<ServerModel> list = dao.getDataByRead(false);
          list.forEach((s) -> {
              System.out.println("ssssssssssssssssss"+s.getMsgChat());
        });
      }
}
