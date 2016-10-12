/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.newbie.chatroom.dao.tablemodel;

import com.blogspot.newbie.chatroom.model.ServerModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author agus
 */
public class ServerTableModel extends AbstractTableModel{
    private List<ServerModel> list = new ArrayList<>();
    final private String kelasHeader[] = {"User", "Messenger", "Type", "Time"};//(id dan kelas) berdasarkan fild yang ada di database. beleh di rubah tetapih jumlah harus sesuai dengan yang ada di database

    public ServerTableModel(List<ServerModel> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return kelasHeader.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ServerModel model = list.get(rowIndex);
        
        switch(columnIndex){//jumlah dan fild berdasarkan yang ada di database
            case 0 : return model.getUserChat();
            case 1 : return model.getMsgChat();
            case 2 : return model.getMsgType();
            case 3 : return model.getTimeChat();
            default:return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return kelasHeader[columnIndex];
    }
}
