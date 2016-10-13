/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.newbie.chatroom;

import com.blogspot.newbie.chatroom.dao.ClientDao;
import com.blogspot.newbie.chatroom.dao.ClientDaoImpl;
import com.blogspot.newbie.chatroom.dao.ServerDao;
import com.blogspot.newbie.chatroom.dao.ServerDaoImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author agus suhardi<agus.suhardii@gmail.com>
 */
public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;
    private static final ServerDao SERVER_DAO;
    private static final ClientDao CLIENT_DAO;

    static {
        try {
            SESSION_FACTORY = new Configuration().configure()
                    .buildSessionFactory();
            SERVER_DAO = new ServerDaoImpl(SESSION_FACTORY);
            CLIENT_DAO = new ClientDaoImpl(SESSION_FACTORY);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static ServerDao getServerDao() {
        return SERVER_DAO;
    }

    public static ClientDao getClientDao() {
        return CLIENT_DAO;
    }
}
