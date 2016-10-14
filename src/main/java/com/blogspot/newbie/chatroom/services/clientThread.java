/*
* 


 */
package com.blogspot.newbie.chatroom.services;

import com.blogspot.newbie.chatroom.HibernateUtil;
import com.blogspot.newbie.chatroom.dao.ServerDao;
import com.blogspot.newbie.chatroom.model.ServerModel;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author agus suhardi<agus.suhardii@gmail.com>
 */
/**
 *
 * @author mohammed
 */
// For every client's connection we call this class
public class clientThread extends Thread {

    private String clientName = null;
    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private int maxClientsCount;

    public clientThread(Socket clientSocket, clientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        clientThread[] threads = this.threads;

        try {
            /*
       * Create input and output streams for this client.
             */
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            String name;
            while (true) {
                os.println("NwebieChat > Enter your name.");
                name = is.readLine().trim();
                if (name.indexOf('@') == -1) {
                    break;
                } else {
                    os.println("NwebieChat > The name should not contain '@' character.");
                }
            }

            /* Welcome the new the client. */
            os.println("NwebieChat > " + name + " to our chat room.\n"
                    + "NwebieChat > more information, enter 'help' for your line");
            //save to database
            saveToDatabase(name, "Join to Server", "public");
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] == this) {
                        clientName = "@" + name;
                        break;
                    }
                }
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] != this) {
                        threads[i].os.println("NwebieChat > *** A new user " + name + " entered the chat room !!! ***");
                    }
                }
            }
            /* Start the conversation. */
            while (true) {
                String line = is.readLine();
                if (line.startsWith("/quit")) {
                    break;
                }
                /* If the message is private sent it to the given client. */
                if (line.startsWith("@")) {
                    String[] words = line.split("\\s", 2);
                    if (words.length > 1 && words[1] != null) {

                        words[1] = words[1].trim();
                        if (!words[1].isEmpty()) {
                            synchronized (this) {
                                for (int i = 0; i < maxClientsCount; i++) {
                                    if (threads[i] != null && threads[i] != this
                                            && threads[i].clientName != null
                                            && threads[i].clientName.equals(words[0])) {
                                        threads[i].os.println("<" + name + "> " + words[1]);

                                        /*
                     * Echo this message to let the client know the private
                     * message was sent.
                                         */
                                        this.os.println("<" + name + "> " + words[1]);// ini telah di ganti
                                        //save to db private messange
                                        saveToDatabase(name, words[1], "private messange to " + threads[i].clientName);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    /* The message is public, broadcast it to all other clients. */
                    synchronized (this) {
                        for (int i = 0; i < maxClientsCount; i++) {
                            if (threads[i] != null && threads[i].clientName != null) {

                                threads[i].os.println("<" + name + "> " + line);
                                //save to db
                                saveToDatabase(name, line, "public");

                                if (line.equalsIgnoreCase("/cek")) {
                                    os.println(threads[i].clientName + " :Online");
                                }

                            }
                        }
                    }
                }
            }
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] != this
                            && threads[i].clientName != null) {
                        threads[i].os.println("NwebieChat > The user " + name
                                + " is leaving the chat room !!! ");
                    }
                }
            }
            os.println("NwebieChat > *** Bye " + name + " ***");
            //save if client close
            saveToDatabase(name, "Close Connection", "Public");

            /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
             */
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == this) {
                        threads[i] = null;
                    }
                }
            }
            /*
       * Close the output stream, close the input stream, close the socket.
             */
            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }

    //metode acces database
    private final static ServerDao dao = HibernateUtil.getServerDao();

    private static void saveToDatabase(String user, String msg, String msgType) {
        //setting unix id from time
        SimpleDateFormat f = new SimpleDateFormat("yymm-dd-HHmmss");
        Date waktu = new Date();
        String hasil = f.format(waktu);

        //save to database using model class
        ServerModel data = new ServerModel();
        data.setIdChat(hasil);
        data.setUserChat(user);
        data.setMsgChat(msg);
        data.setTimeChat(new Date());
        data.setMsgType(msgType);
        dao.save(data);
    }

    /*
    public void sendPanddingMsg(String msgType, boolean e) {
        System.out.println("param 1 :" + msgType);
        System.out.println("param 2 :" + e);

        List<ServerModel> list = dao.getDataByReadAndUser(msgType, e);
        for (ServerModel m : list) {
            System.out.println("ini data yang belum di:       " + m.getMsgType());

        }
    }
     */
}
