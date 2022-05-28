/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The listener on canvas. Accept user's action and generate the corresponding drawing record then board it to other users.
 */
package User;

import Shape.ShapeDraw;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UIListener implements ActionListener, MouseListener, MouseMotionListener {

    // the start coordinate and end coordinate of mouse
    private int x1, x2, y1, y2;
    // the user's action
    private static Action action;
    // the color be used, the default is Black
    private static Color color = Color.BLACK;

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // get the start point
        x1 = e.getX();
        y1 = e.getY();

        if (action == Action.Text) {
            // get the input
            String input;
            input = JOptionPane.showInputDialog("Please input the text.");
            // board the action
            if (input != null) {
                JSONObject newRecord = ShapeDraw.generateRecord(x1, 0, y1, 0, color, Action.Text, input);
                User.boardNewRecord(newRecord);
            }
        }
    }

    /**
     * The method to get start point of shape when user press mouse
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // get the start point
        x1 = e.getX();
        y1 = e.getY();
    }

    /**
     * The method to get end point of shape when user press mouse
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // get the end point
        x2 = e.getX();
        y2 = e.getY();

        // act according to the action type
        if (action == Action.Circle){
            // generate the record
            JSONObject newRecord = ShapeDraw.generateRecord(x1, x2, y1, y2, color, Action.Circle, null);
            User.boardNewRecord(newRecord);
        }
        else if(action == Action.Line){
            JSONObject newRecord = ShapeDraw.generateRecord(x1, x2, y1, y2, color, Action.Line, null);
            User.boardNewRecord(newRecord);
        }
        else if (action == Action.Rectangle){
            JSONObject newRecord = ShapeDraw.generateRecord(x1, x2, y1, y2, color, Action.Rectangle, null);
            User.boardNewRecord(newRecord);
        }
        else if (action == Action.Triangle){
            JSONObject newRecord = ShapeDraw.generateRecord(x1, x2, y1, y2, color, Action.Triangle, null);
            User.boardNewRecord(newRecord);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    /**
     * Color setter
     * @param color
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * Action setter
     * @param action
     */
    public void setAction(Action action){
        this.action = action;
    }

    public void boardMsg(String message){
        User.boardMsg(message);
    }
}
