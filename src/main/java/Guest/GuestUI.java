/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The Guest UI.
 */
package Guest;

import User.UserUI;

import com.alibaba.fastjson.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GuestUI extends UserUI {
    /**
     * Create the application.
     */
    public GuestUI(){
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
    }

    /**
     * Identification mark
     */
    private void identityMark(){
        JLabel identity = new JLabel("Welcome to the white board, You are the Guest");
        identity.setFont(new Font("Normal", Font.PLAIN, 20));
        identity.setBounds(5, 10, 500, 20);
        wholeWindow.getContentPane().add(identity);
    }

    /**
     * Notify the user has been kicked
     */
    protected void kickNotification(){
        JOptionPane.showMessageDialog(null,  "You are removed by the user", "Notification", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    /**
     * Notify the user, the host close the white board
     */
    protected void hostQuit(){
        JOptionPane.showMessageDialog(null,  "The Host close the White Board", "Notification", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    @Override
    protected void closeWindowEvent(){
        Guest.guestQuit();
    }

}
