/* importing various GUI(awt,swing and their events) components*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/*importing TCP/IP related classes, io package for server-client commmunication and util package for some purpose*/
import java.net.*;
import java.io.*;
import java.util.*;


/*class Server {
    public void getIP() {
        System.out.println(GroupChat.port.getText());
    }    
}*/

public class GroupChat  {
    //declaring required GUI components
    public static JFrame mainFrame;
    public static JTextField msg,ip,port,nickName;
    public static JTextArea msgText;
    public static JButton connect,disconnect,privateChat,exitChat;
    public static JList onlineUsers;
    public static JToggleButton turnOnChat;
    
    //TCP/IP variables
    public static String hostIP = "127.0.0.1";
    public static int portNo = 9090; 
    
    //chat variables 
    public static boolean turnOffChat = false;
    public static String users[] = {"Bomber","Harry","RJ","KNC","Jordan","Error","Raaz","Legend","Fucker","Tony"};
    /*public static ArrayList<String> users = new ArrayList<String>();
    users.add("Bomber");
    users.add("Harry");
    users.add("Jordan");
    users.add("Raaz");
    users.add("KNC");
    users.add("RJ");
    users.add("Error");
    users.add("Legend");
    users.add("Fucker");*/
    
    public static JPanel optionsPane() {
        JPanel opt1,opt2,opt3,opt4,optPanel;
        optPanel = new JPanel(new GridLayout(4,1));

        opt1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        opt1.add(new JLabel("Host IP: "));
        ip = new JTextField(10);
        ip.setText(hostIP);
        opt1.add(ip);
        optPanel.add(opt1);

        opt2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        opt2.add(new JLabel("Port: "));
        port = new JTextField(5);
        port.setText((new Integer(portNo)).toString());
        opt2.add(port);
        optPanel.add(opt2);

        opt3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        opt3.add(new JLabel("Nick Name: "));
        nickName = new JTextField(10);
        opt3.add(nickName);
        optPanel.add(opt3);

        opt4 = new JPanel(new GridLayout(2,1,0,10));
        connect = new JButton("CONNECT");
        connect.setMnemonic(KeyEvent.VK_C);
        connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String ipAddr = ip.getText();
                    int chatPort = Integer.parseInt(port.getText());
                    ServerSocket ss = new ServerSocket(chatPort);
                    Socket s = ss.accept();
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    
                }
                catch(Exception e) {
                }
                disconnect.setEnabled(true);
                connect.setEnabled(false);
            }
        });
        connect.setEnabled(true);
        disconnect = new JButton("DISCONNECT");
        disconnect.setMnemonic(KeyEvent.VK_D);
        disconnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                connect.setEnabled(true);
                disconnect.setEnabled(false);
            }
        });
        disconnect.setEnabled(false);
        opt4.add(connect);
        opt4.add(disconnect);
        optPanel.add(opt4);
        return optPanel;                      
    }
    public static void initGUI() {
        /*Actually, I've divided total GUI into three parts. 
            1)Optaions Pane
            2)Chat Pane
            3)Users Pane
        */
        JPanel optPane = optionsPane(); //OptionsPane
        
        //ChatPane
        JPanel chatPane = new JPanel(new BorderLayout());   
        msgText = new JTextArea(10,40);
        msgText.setEditable(false);
        msgText.setLineWrap(true);
        msgText.setForeground(Color.green);
        JScrollPane sb = new JScrollPane(msgText,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        msg = new JTextField();
        msg.setEnabled(true);
        msg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        chatPane.add(msg,BorderLayout.SOUTH);
        chatPane.add(sb,BorderLayout.CENTER);
        chatPane.setSize(250,250);
        
        //UsersPane
        JPanel usersPane = new JPanel(new BorderLayout());
        JPanel chatOptPane = new JPanel(new GridLayout(2,1,0,30));
        
        onlineUsers = new JList(users);
        onlineUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        onlineUsers.setSize(onlineUsers.getPreferredScrollableViewportSize());
        onlineUsers.setSelectionBackground(Color.blue);
        onlineUsers.setSelectionForeground(Color.white);
        JScrollPane sp = new JScrollPane(onlineUsers,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        onlineUsers.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent le) {
                privateChat.setEnabled(true);
            }
        });
        
        JPanel chatButtons = new JPanel(new GridLayout(3,1,0,10));
        privateChat = new JButton("Private Chat");
        privateChat.setMnemonic(KeyEvent.VK_P);
        if(onlineUsers.isSelectionEmpty()) privateChat.setEnabled(false);
        else privateChat.setEnabled(true);
        
        turnOnChat = new JToggleButton("Turn On/off Chat");
        
        exitChat = new JButton("Exit Chat");
        exitChat.setMnemonic(KeyEvent.VK_E);
        exitChat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        chatButtons.add(privateChat);
        chatButtons.add(turnOnChat);
        chatButtons.add(exitChat);
        chatOptPane.add(sp);
        chatOptPane.add(chatButtons);
        
        usersPane.add(chatOptPane,BorderLayout.CENTER);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(optPane,BorderLayout.WEST);
        panel.add(chatPane,BorderLayout.CENTER);
        panel.add(usersPane,BorderLayout.EAST);
        
        mainFrame = new JFrame("GroupChat v1.0");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setContentPane(panel);
        mainFrame.setSize(mainFrame.getPreferredSize());
        mainFrame.setLocation(200,200);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }    
    public static void main(String []cla) {
        initGUI();    
    }
}
