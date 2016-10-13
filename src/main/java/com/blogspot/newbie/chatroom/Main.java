package com.blogspot.newbie.chatroom;

import com.blogspot.newbie.chatroom.services.ChatClient;
import com.blogspot.newbie.chatroom.services.ChatClientCs;
import com.blogspot.newbie.chatroom.services.MultiThreadChatServerSync;
import com.blogspot.newbie.chatroom.view.LogServerView;
import javax.swing.*;

/**
 *
 * @author agus suhardi<agus.suhardii@gmail.com>
 */
//Class to precise who is connected : Client or Server
public class Main {

    public static void main(String[] args) {

        Object[] selectioValues = {"Server", "Client", "Client CS", "Log Server"};
        String initialSection = "Client";

        Object selection = JOptionPane.showInputDialog(null, "Select Chat Menu : ", "NewbieChat", JOptionPane.QUESTION_MESSAGE, null, selectioValues, initialSection);
        if (selection.equals("Server")) {
            String[] arguments = new String[]{};
            MultiThreadChatServerSync.main(arguments);
        } else if (selection.equals("Client")) {
            String IPServer = JOptionPane.showInputDialog("Enter the Server ip adress");
            String[] arguments = new String[]{IPServer};
            ChatClient.main(arguments);
        } else if (selection.equals("Client CS")) {
            String IPServer = JOptionPane.showInputDialog("Enter the Server IP Addres");
            String[] arguments = new String[]{IPServer};
            ChatClientCs.main(arguments);
        } else if (selection.equals("Log Server")) {
            new LogServerView().setVisible(true);
        }

    }

}
