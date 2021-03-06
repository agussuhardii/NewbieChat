package com.blogspot.newbie.chatroom.dao;

import com.blogspot.newbie.chatroom.model.ServerModel;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author agus suhardi<agus.suhardii@gmail.com>
 */
public class ServerDaoImpl implements ServerDao {

    private final SessionFactory sessionFactory;

    public ServerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    @Override
    public void save(ServerModel dataServer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.beginTransaction();
            session.save(dataServer);
            session.getTransaction();
            transaction.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void update(ServerModel dataServer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.beginTransaction();
            session.update(dataServer);
            session.getTransaction();
            transaction.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(ServerModel dataServer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.beginTransaction();
            session.delete(dataServer);
            session.getTransaction();
            transaction.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public ServerModel getServer(String id_chat) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.beginTransaction();
            ServerModel serverModel = session.get(ServerModel.class, id_chat);
            session.getTransaction().commit();
            return serverModel;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<ServerModel> getDataChatServers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.beginTransaction();
            List<ServerModel> list = session.createCriteria(ServerModel.class).list();
            session.getTransaction().commit();
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }
    /*
    @Override
    public List<ServerModel> getDataByRead(boolean i) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "FROM ServerModel s where s.msgRead="+i;
        try {
            session.beginTransaction();
            Query q = session.createQuery(sql);
            List<ServerModel> list = q.list();
            session.getTransaction().commit();
            return list;
        } catch (Exception e) {
            System.out.println("Pesan "+e.getMessage());
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<ServerModel> getDataByReadAndUser(String user, boolean i) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "FROM ServerModel s where s.msgRead="+i+" and";
        try {
            session.beginTransaction();
            Query q = session.createQuery(sql);
            List<ServerModel> list = q.list();
            session.getTransaction().commit();
            return list;
        } catch (Exception e) {
            System.out.println("Pesan "+e.getMessage());
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }}
     */

}
