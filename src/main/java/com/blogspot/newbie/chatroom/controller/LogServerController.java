/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.newbie.chatroom.controller;

import com.blogspot.newbie.chatroom.HibernateUtil;
import com.blogspot.newbie.chatroom.dao.ServerDao;
import com.blogspot.newbie.chatroom.dao.tablemodel.ServerTableModel;
import com.blogspot.newbie.chatroom.model.ServerModel;
import com.blogspot.newbie.chatroom.view.LogServerView;
import java.util.List;

/**
 *
 * @author agus suhardi<agus.suhardii@gmail.com>
 */
public final class LogServerController {
    LogServerView view;

    public LogServerController(LogServerView view) {
        this.view = view;
        showData();
    }
    
    
    private ServerTableModel tableModel;
    private List<ServerModel> list;
    private final static ServerDao dao = HibernateUtil.getServerDao();// untuk koneksi ke database
    
    
    //methode untuk menampilkan data ke table model
    public void showData() {
        list = dao.getDataChatServers();
        tableModel = new ServerTableModel(list);
        this.view.getTblLog().setModel(tableModel);
    }
}
