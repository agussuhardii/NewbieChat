/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.newbie.chatroom.dao.tablemodel;

import com.blogspot.newbie.chatroom.model.ClientModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author agus suhardi<agus.suhardii@gmail.com>
 */
public class ClientTableModel extends AbstractTableModel {

    private List<ClientModel> list = new ArrayList<>();
    final private String Header[] = {"Kode", "Nama", "Keterangan"};//(id dan kelas) berdasarkan fild yang ada di database. beleh di rubah tetapih jumlah harus sesuai dengan yang ada di database

    public ClientTableModel(List<ClientModel> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return Header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClientModel model = list.get(rowIndex);

        switch (columnIndex) {//jumlah dan fild berdasarkan yang ada di database
            case 0:
                return model.getKode();
            case 1:
                return model.getNama();
            case 2:
                return model.getKeterangan();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return Header[columnIndex];
    }
}
