/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The Host UI.
 */
package Host;

import User.UserUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class HostUI extends UserUI {

    // the file's path
    String filePath;
    // the host
    Host host;

    public HostUI(){
        // initialize the window
        initialize();
        // initialize the canvas
        canvasArea();
        // the chat component
        chatArea();
        // identity mark
        identityMark();
        // the chat window
        userListArea();
        // the host's control
        hostControl();
    }

    Boolean judgeRequest(String userName){
        int flag = JOptionPane.showConfirmDialog(null, userName + " wants to join the whiteboard\n" + "approve or not?","New Join Request", JOptionPane.YES_NO_OPTION);
        if(flag == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    void identityMark(){
        JLabel terminalOutput = new JLabel("Welcome, You are the Host");
        terminalOutput.setFont(new Font("Normal", Font.PLAIN, 12));
        terminalOutput.setBounds(600, 15, 300, 25);
        container.add(terminalOutput);
    }

    void hostControl(){
        //Canvas
        JButton NewButton = new JButton("New");
        NewButton.setBounds(0, 10, 90, 30);
        NewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    host.newCanvas();
                } catch (RemoteException ex) {
                    notificationWindow("Remote Error when new a canvas");
                }
            }
        });
        container.add(NewButton);

        JButton OpenButton = new JButton("Open");
        OpenButton.setBounds(90, 10, 90, 30);
        OpenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // let the user choose file
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String fileAbsolutePath = fileChooser.getSelectedFile().getAbsolutePath();
                    filePath = fileAbsolutePath;
                    host.loadFromFile(filePath);
                }
            }
        });
        container.add(OpenButton);

        JButton SaveButton = new JButton("Save");
        SaveButton.setBounds(180, 10, 90, 30);
        SaveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (filePath != null) {
                    host.saveToFile(filePath);
                }else{
                    JOptionPane.showMessageDialog(null, "You did not open a file before. " +
                            "Try to use 'Save As' button to save this canvas as a new file",
                            "Notification", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        container.add(SaveButton);

        JButton SaveAsButton = new JButton("Save as");
        SaveAsButton.setBounds(270, 10, 90, 30);
        SaveAsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // let the user choose file
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String fileAbsolutePath = fileChooser.getSelectedFile().getAbsolutePath();
                    filePath = fileAbsolutePath;
                    host.saveToFile(filePath);
                }
            }
        });
        container.add(SaveAsButton);

        JButton CloseButton = new JButton("Close");
        CloseButton.setBounds(360, 10, 90, 30);
        CloseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    wholeWindow.setVisible(false);
                    host.closeWhiteBoard();
                } catch (RemoteException ex) {
                    notificationWindow("Remote Error when close the window");
                }
            }
        });
        container.add(CloseButton);

        JButton KickButton = new JButton("Kick");
        KickButton.setBounds(450, 10, 90, 30);
        KickButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String userName = JOptionPane.showInputDialog("Please input the user name you want to kick.");
                new Thread(){
                    public void run(){
                        try {
                            host.kickGuest(userName);
                        } catch (RemoteException ex) {
                            notificationWindow("Remote Error when kick the user");
                        }
                    }
                }.start();
            }
        });
        container.add(KickButton);
    }

    @Override
    protected void closeWindowEvent(){
        try {
            wholeWindow.setVisible(false);
            host.closeWhiteBoard();
        } catch (RemoteException e) {
            notificationWindow("Remote Error when close the window");
        }
    }
}
