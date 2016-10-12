package com.blogspot.newbie.chatroom.controller;

import com.blogspot.newbie.chatroom.HibernateUtil;
import com.blogspot.newbie.chatroom.dao.ClientDao;
import com.blogspot.newbie.chatroom.dao.tablemodel.ClientTableModel;
import com.blogspot.newbie.chatroom.model.ClientModel;
import com.blogspot.newbie.chatroom.view.ClientCsDataView;
import com.sun.xml.internal.txw2.TXW;
import java.util.List;
import javax.swing.JOptionPane;

public class ClientCsDataController {

    ClientCsDataView view;

    public ClientCsDataController(ClientCsDataView view) {
        this.view = view;
    }

    private final static ClientDao dao = HibernateUtil.getClientDao();
    private List<ClientModel> list;
    ClientTableModel tableModel;

    public void showData() {
        list = dao.getClient();
        tableModel = new ClientTableModel(list);
        view.getTblDataCsClient().setModel(tableModel);
    }

    public void save() {
        ClientModel data = new ClientModel();
            data.setNama(view.getTxtNama().getText());
            data.setKeterangan(view.getAreaKeterangan().getText());
            data.setKode(getCountRecord()+1);
        dao.save(data);
        
        showData();
        clear();

    }

    public void change() {
        if(view.getBtnChange().getText().equals("Change")){
            String kodeS = JOptionPane.showInputDialog("Masukan Kode yang akan di ubah");
            view.getTxtKode().setText(kodeS);
            ClientModel m = dao.getKode(Integer.valueOf(kodeS));

            view.getTxtNama().setText(m.getNama());
            view.getAreaKeterangan().setText(m.getKeterangan());
            view.getBtnChange().setText("Ok");
        
        }else if(view.getBtnChange().getText().equals("Ok")){
            ClientModel m = new ClientModel();
                m.setKode(Integer.valueOf(view.getTxtKode().getText()));
                m.setNama(view.getTxtNama().getText());
                m.setKeterangan(view.getAreaKeterangan().getText());
            dao.update(m);
            view.getBtnChange().setText("Change");
            showData();
            clear();
        }
        
    }

    public void delete() {
        String kodeS = JOptionPane.showInputDialog("Masukan Kode Yang akan di Hapus");
        int i = JOptionPane.showConfirmDialog(null, "Yakin mau Hapus", "Hapus", JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.YES_OPTION) {

            ClientModel data = new ClientModel();
            data.setKode(Integer.valueOf(kodeS));
            data.setNama(view.getTxtNama().getText());
            data.setKeterangan(view.getAreaKeterangan().getText());
            dao.delete(data);
            clear();
            showData();
        }
    }

    public int getCountRecord() {
        List<ClientModel> l = dao.getClient();
        return l.size();
    }

    public void clear() {
        view.getTxtNama().setText("");
        view.getAreaKeterangan().setText("");
        view.getTxtKode().setText("");

    }

}
