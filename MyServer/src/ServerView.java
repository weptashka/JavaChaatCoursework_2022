
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Polina
 */
public class ServerView extends javax.swing.JFrame {

    /**
     * Creates new form Server
     */
    ServerSocket serverSocket;
    HashMap clientCall = new HashMap();
    int port = 2089;

    public ServerView() {
        try {
            initComponents();
            serverSocket = new ServerSocket(port);
            this.sStatusLabel.setText("Server Started on port " + port);

            new ClientAccept().start();// (ClientAccept -- is the thread) this will create a thread for client
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class ClientAccept extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();// create a socket for client
                    String i = new DataInputStream(clientSocket.getInputStream()).readUTF();// this will receive the username sent from client register view
                    if (clientCall.containsKey(i)) {
                        DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());
                        dout.writeUTF("User with such ID is already entered!");
                    } else {
                        clientCall.put(i, clientSocket);
                        serverChatArea.append("< " + i + " > " + " Joined chat\n");
                        DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());
                        dout.writeUTF("");
                        new MessageReader(clientSocket, i).start();
                        new PrepareClientList().start();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class MessageReader extends Thread { // this class reads the messages coming from client and take appropriate actions

        Socket s;
        String ID;

        private MessageReader(Socket s, String userName) {
            this.s = s;
            this.ID = userName;
        }

        @Override
        public void run() {
            while (!clientCall.isEmpty()) {
                try {
                    String message = new DataInputStream(s.getInputStream()).readUTF(); // read message from client
                    if (message.equals("wequrypisjbh124567890QWERTYUIOPASDFGHJKL:ZXCVBNM<>?")) {//(хз, но наверное если такое сообщение пришло от клаента, то его выкидывает)
                        clientCall.remove(ID);
                        serverChatArea.append(ID + "Went out.\n");
                        new PrepareClientList().start();

                        Set k = clientCall.keySet();
                        Iterator itr = k.iterator();
                        while (itr.hasNext()) {
                            String key = (String) itr.next();
                            if (!key.equalsIgnoreCase(ID)) {
                                try {
                                    new DataOutputStream(((Socket) clientCall.get(key)).getOutputStream()).writeUTF("< " + ID + " >" + message);
                                } catch (Exception ex) {
                                    clientCall.remove(key);
                                    serverChatArea.append(key + ": removed.\n");
                                    new PrepareClientList().start();
                                }
                            }
                        }
                    } else if (message.contains("2134123@@@@@#42")) {
                        message = message.substring(20);
                        StringTokenizer st = new StringTokenizer(message, ":");
                        String id = st.nextToken();
                        message = st.nextToken();
                        try {
                            new DataOutputStream(((Socket) clientCall.get(id)).getOutputStream())
                                    .writeUTF("< " + ID + " to " + id + " > " + message);
                        } catch (Exception ex) {
                            clientCall.remove(id);
                            serverChatArea.append(id + " removed!\n");
                            new PrepareClientList().start();
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    class PrepareClientList extends Thread {

        @Override
        public void run() {
            try {
                String ids = "";
                Set k = clientCall.keySet();
                Iterator itr = k.iterator();
                while (itr.hasNext()) {
                    String key = (String) itr.next();
                    ids += key + ",";
                }
                if (ids.length() != 0) {
                    ids = ids.substring(0, ids.length() - 1);
                }

                itr = k.iterator();
                while (itr.hasNext()) {
                    String key = (String) itr.next();
                    try {
                        new DataOutputStream(((Socket) clientCall.get(key)).getOutputStream()).writeUTF(":;.,/=" + ids);
                    } catch (Exception ex) {
                        clientCall.remove(key);
                        serverChatArea.append(key + " removed!");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        serverChatArea = new javax.swing.JTextArea();
        serverSideLabel = new javax.swing.JLabel();
        serverStatusLabel = new javax.swing.JLabel();
        sStatusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MyServer");
        setMinimumSize(new java.awt.Dimension(400, 400));

        jPanel1.setBackground(new java.awt.Color(45, 40, 62));
        jPanel1.setMinimumSize(new java.awt.Dimension(400, 400));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(377, 356));

        serverChatArea.setColumns(20);
        serverChatArea.setRows(5);
        jScrollPane1.setViewportView(serverChatArea);

        serverSideLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        serverSideLabel.setForeground(new java.awt.Color(209, 215, 224));
        serverSideLabel.setText("Server Side");

        serverStatusLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        serverStatusLabel.setForeground(new java.awt.Color(128, 43, 177));
        serverStatusLabel.setText("Server Status:");

        sStatusLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        sStatusLabel.setForeground(new java.awt.Color(209, 215, 224));
        sStatusLabel.setText(".....................");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(serverStatusLabel)
                        .addGap(18, 18, 18)
                        .addComponent(sStatusLabel))
                    .addComponent(serverSideLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(serverSideLabel)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serverStatusLabel)
                    .addComponent(sStatusLabel))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel sStatusLabel;
    private javax.swing.JTextArea serverChatArea;
    private javax.swing.JLabel serverSideLabel;
    private javax.swing.JLabel serverStatusLabel;
    // End of variables declaration//GEN-END:variables
}
