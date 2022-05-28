/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The Super Class for the Host UI and Guest UI. This class contains common user functions like drawing.
 */
package User;

import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class UserUI extends Thread{
    private ArrayList<JSONObject> canvasRecord;
    public JFrame wholeWindow;
    protected Container container;
    private Canvas canvas;
    private UIListener uiListener;
    private JTextArea chatArea;
    private JTextArea userList;

    /**
     * Launch the application.
     */
    @Override
    public void run() {
        try {
            wholeWindow.setVisible(true);
        } catch (Exception e) {
            notificationWindow("Can not open the UI window");
        }
    }
    /**
     * Initialize the contents of the frame.
     */
    protected void initialize() {
        // generate the JFrame
        wholeWindow = new JFrame();
        wholeWindow.setBounds(100, 100, 800, 600);
        wholeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wholeWindow.getContentPane().setLayout(null);
        // get the contentpane
        container = wholeWindow.getContentPane();

        // the listener to the window
        wholeWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                super.windowClosing(e);
                // do the event
                closeWindowEvent();
            }
        });
    }

    /**
     * The canvas area
     */
    protected void canvasArea() {
        // generate the canvas
        canvas = new Canvas();
        canvas.setBounds(6, 79, 527, 490);
        canvas.setBackground(Color.white);
        container.add(canvas);
        // Listener on canvas
        uiListener = new UIListener();
        canvas.addMouseListener(uiListener);

        //Canvas
        JButton CircleButton = new JButton("Circle");
        CircleButton.setBounds(0, 44, 90, 30);
        CircleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                uiListener.setAction(Action.Circle);
            }
        });
        container.add(CircleButton);

        JButton RectangleButton = new JButton("Rectangle");
        RectangleButton.setBounds(90, 44, 90, 30);
        RectangleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                uiListener.setAction(Action.Rectangle);
            }
        });
        container.add(RectangleButton);

        JButton TriangleButton = new JButton("Triangle");
        TriangleButton.setBounds(180, 44, 90, 30);
        TriangleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                uiListener.setAction(Action.Triangle);
            }
        });
        container.add(TriangleButton);

        JButton LineButton = new JButton("Line");
        LineButton.setBounds(270, 44, 90, 30);
        LineButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                uiListener.setAction(Action.Line);
            }
        });
        container.add(LineButton);

        JButton TextButton = new JButton("Text");
        TextButton.setBounds(360, 44, 90, 30);
        TextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                uiListener.setAction(Action.Text);
            }
        });
        container.add(TextButton);

        JButton ColorButton = new JButton("Color");
        ColorButton.setBounds(450, 44, 90, 30);
        ColorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color color = JColorChooser.showDialog(null, "Choose a color you like", null);
                uiListener.setColor(color);
            }
        });
        container.add(ColorButton);
    }

    /**
     * The chat area
     */
    protected void chatArea(){
        // typing area
        JTextArea textField = new JTextArea();
        textField.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, Color.LIGHT_GRAY));
        textField.setBounds(540, 520, 200, 50);
        textField.setColumns(10);
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);

        JScrollPane scrollText = new JScrollPane();
        scrollText.setBounds(540, 520, 200, 50);
        scrollText.setViewportView(textField);
        container.add(scrollText);

        // send button
        JButton sendButton = new JButton("SEND");
        sendButton.setBounds(750, 520, 50, 50);
        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // board the message if there is input
                if (!textField.getText().equals("")) {
                    uiListener.boardMsg(textField.getText());
                    // empty the input bar
                    textField.setText("");
                }
            }
        });
        container.add(sendButton);

        JLabel chatBoxLogo = new JLabel("Chat Box");
        chatBoxLogo.setFont(new Font("Normal", Font.PLAIN, 15));
        chatBoxLogo.setBounds(540, 230, 255, 20);
        container.add(chatBoxLogo);

        // show the chat window
        chatArea = new JTextArea();
        chatArea.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, Color.LIGHT_GRAY));
        chatArea.setBounds(540, 260, 255, 250);
        chatArea.setColumns(10);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        JScrollPane scrollChat = new JScrollPane();
        scrollChat.setBounds(540, 260, 255, 250);
        scrollChat.setViewportView(chatArea);
        container.add(scrollChat);
    }

    protected void userListArea(){

        JLabel usersLogo = new JLabel("Users");
        usersLogo.setFont(new Font("Normal", Font.PLAIN, 15));
        usersLogo.setBounds(540, 48, 255, 20);
        container.add(usersLogo);

        // show the chat window
        userList = new JTextArea();
        userList.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, Color.LIGHT_GRAY));
        userList.setBounds(540, 79, 255, 130);
        userList.setColumns(10);
        userList.setLineWrap(true);
        userList.setEditable(false);
        userList.setWrapStyleWord(true);

        JScrollPane userListScroll = new JScrollPane();
        userListScroll.setBounds(540, 79, 255, 130);
        userListScroll.setViewportView(userList);
        container.add(userListScroll);
    }

    /**
     * Canvas getter
     * @return the canvas
     */
    public Canvas getCanvas(){
        return canvas;
    }

    /**
     * Canvas record list getter
     * @return the canvas list
     */
    public ArrayList<JSONObject> getCanvasRecord(){
        return canvasRecord;
    }

    /**
     * Get the chat area
     * @return
     */
    public JTextArea getChatArea(){
        return chatArea;
    }

    /**
     * Get the user list
     * @return
     */
    public JTextArea getUserList() {
        return userList;
    }


    /**
     * The notification window
     */
    public static void notificationWindow(String message){
        JOptionPane.showMessageDialog(null, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * The event when the window is closed
     * Different event will occur according to user's type, the Host and Guest should override this method
     */
    protected void closeWindowEvent(){}

    /**
     * Set the canvas record list
     * @param canvasRecord
     */
    public void setCanvasRecord(ArrayList<JSONObject> canvasRecord){
        this.canvasRecord = canvasRecord;
    }
}
