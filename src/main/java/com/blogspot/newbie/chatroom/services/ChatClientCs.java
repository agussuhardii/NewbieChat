package com.blogspot.newbie.chatroom.services;

import com.blogspot.newbie.chatroom.HibernateUtil;
import com.blogspot.newbie.chatroom.dao.ClientDao;
import com.blogspot.newbie.chatroom.model.ClientModel;
import com.blogspot.newbie.chatroom.view.AboutView;
import com.blogspot.newbie.chatroom.view.ClientCsDataView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
//import org.apache.derby.iapi.util.StringUtil;

/**
 *
 * @author agus suhardi<agus.suhardii@gmail.com>
 */
// Class to manage Client chat Box.
public class ChatClientCs {

    /**
     * Chat client access
     */
    static class ChatAccess extends Observable {

        private Socket socket;
        private OutputStream outputStream;

        @Override
        public void notifyObservers(Object arg) {
            super.setChanged();
            super.notifyObservers(arg);
        }

        /**
         * Create socket, and receiving thread
         */
        public void InitSocket(String server, int port) throws IOException {
            socket = new Socket(server, port);
            outputStream = socket.getOutputStream();

            Thread receivingThread = new Thread() {
                @Override
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            notifyObservers(line);

                            String nama = line.substring(line.indexOf("<") + 1, line.indexOf(">"));
                            if (!nama.equalsIgnoreCase("NwebieChat")) {

                                String[] helpYou = line.split(">");
                                if (helpYou.length != -1) {
                                    String h1 = helpYou[1].trim();
                                    if (h1.equalsIgnoreCase("help")) {
                                        sendInfo(nama);
                                    } else if (cekNumber(h1) == true) {
                                        int inputNumber = Integer.valueOf(h1);
                                        int maxNumber = getCountRecord();
                                        if (inputNumber <= maxNumber && inputNumber >= 1) {
                                            senByOneInfo(h1, nama);
                                        } else if (inputNumber == 0) {
                                            send("@" + nama + " Mohon menunggu, sedang di hubungkan dengan CS kami");
                                            JOptionPane.showMessageDialog(null, "CLient sedang meminta bantuan anda");
                                        } else {
                                            send("@" + nama + " Pilihan yang anda masukan salah");
                                        }

                                    } else if (h1.equalsIgnoreCase("/about")) {
                                        new AboutView(null, true).setVisible(true);
                                    }
                                }
                            }

                        }
                    } catch (IOException ex) {
                        notifyObservers(ex);
                    }
                }
            };
            receivingThread.start();
        }

        private static final String CRLF = "\r\n"; // newline

        /**
         * Send a line of text
         */
        public void send(String text) {
            try {
                outputStream.write((text + CRLF).getBytes());
                outputStream.flush();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }

        //metode acces database
        private final static ClientDao dao = HibernateUtil.getClientDao();

        public void sendInfo(String nama) {
            send("@" + nama + " Pilih Informasi yang di perlukan : ");
            java.util.List<ClientModel> list = dao.getClient();
            for (ClientModel l : list) {

                send("@" + nama + " " + l.getKode() + " : " + l.getNama());
            }
            send("@" + nama + " 0 : Hubungi Operator");
        }

        //send msg from db by selected number
        public void senByOneInfo(String info, String nama) {
            int i = Integer.valueOf(info);
            ClientModel m = dao.getKode(i);
            send("@" + nama + " " + m.getKeterangan());

        }

        //cek inputan number
        public boolean cekNumber(String i) {
            try {
                Integer.valueOf(i);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }

        //get Count dbRecord
        public int getCountRecord() {
            java.util.List<ClientModel> list = dao.getClient();
            return list.size();
        }

        /**
         * Close the socket
         */
        public void close() {
            try {
                socket.close();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
    }

    /**
     * Chat client UI
     */
    static class ChatFrame extends JFrame implements Observer {

        private JTextArea textArea;
        private JTextField inputTextField;
        private JButton sendButton;
        private ChatAccess chatAccess;

        public ChatFrame(ChatAccess chatAccess) {
            this.chatAccess = chatAccess;
            chatAccess.addObserver(this);
            buildGUI();
        }

        /**
         * Builds the user interface
         */
        private void buildGUI() {
            textArea = new JTextArea(20, 50);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            add(new JScrollPane(textArea), BorderLayout.CENTER);

            Box box = Box.createHorizontalBox();
            add(box, BorderLayout.SOUTH);
            inputTextField = new JTextField();
            sendButton = new JButton("Send");
            box.add(inputTextField);
            box.add(sendButton);

            // Action for the inputTextField and the goButton
            ActionListener sendListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String str = inputTextField.getText();

                    //System.out.println("Nama dikirim.............................");
                    if (str != null && str.trim().length() > 0) {
                        chatAccess.send(str); // tidak di terima jika bukan private messenge
                    }
                    if (str.equalsIgnoreCase("/show")) {
                        new ClientCsDataView(null, true).setVisible(true);
                    }

                    inputTextField.selectAll();
                    inputTextField.requestFocus();
                    inputTextField.setText("");
                }
            };
            inputTextField.addActionListener(sendListener);
            sendButton.addActionListener(sendListener);

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    chatAccess.close();
                }
            });
        }

        /**
         * Updates the UI depending on the Object argument
         */
        public void update(Observable o, Object arg) {
            final Object finalArg = arg;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    textArea.append(finalArg.toString());
                    textArea.append("\n");
                }
            });
        }
    }

    public static void main(String[] args) {
        String server = args[0];
        int port = 4444;
        ChatAccess access = new ChatAccess();

        JFrame frame = new ChatFrame(access);
        frame.setTitle("Wellcome CS - connected to " + server + ":" + port);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        try {
            access.InitSocket(server, port);
        } catch (IOException ex) {
            System.out.println("Cannot connect to " + server + ":" + port);
            ex.printStackTrace();
            System.exit(0);
        }
    }
}
