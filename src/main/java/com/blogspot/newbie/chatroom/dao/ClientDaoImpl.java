package com.blogspot.newbie.chatroom.dao;

import com.blogspot.newbie.chatroom.model.ClientModel;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ClientDaoImpl implements ClientDao{

    private final SessionFactory sessionFactory;

    public ClientDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    
    
    @Override
    public ClientModel getKode(int kode) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            ClientModel clientModel = session.get(ClientModel.class, kode);
            session.getTransaction().commit();
            return clientModel;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<ClientModel> getClient() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.beginTransaction();
            List<ClientModel> list = session.createCriteria(ClientModel.class).list();
            session.getTransaction().commit();
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }
}
    

