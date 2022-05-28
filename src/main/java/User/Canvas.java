/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The canvas on whiteboard. Used to accept action request followed by drawing the corresponding shape.
 */
package User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import com.alibaba.fastjson.JSONObject;

import Shape.*;

public class Canvas extends JPanel{

    // the record list
    ArrayList<JSONObject> canvasRecord;
    // the shape drawer
    ShapeDraw shapeDraw;

    /**
     * Set the canvas record
     * @param canvasRecord
     */
    public void setCanvasRecord(ArrayList<JSONObject> canvasRecord){
        this.canvasRecord = canvasRecord;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        // draw all record in record list
        for (JSONObject eachRecord : canvasRecord){
            draw(eachRecord, g);
        }
    }

    /**
     * Accept the record, and do the action in it
     */
    private void draw(JSONObject record, Graphics g){
        // extract all information
        int x1 = (Integer)record.get("startX");
        int x2 = (Integer)record.get("endX");
        int y1 = (Integer)record.get("startY");
        int y2 = (Integer)record.get("endY");
        Color color = (Color) record.get("color");
        Action action = (Action) record.get("action");
        String input = (String) record.get("input");

        // act according to the action type
        if (action == Action.Circle){
            shapeDraw = new ShapeCircle();
            shapeDraw.draw(x1, x2, y1, y2, color, g, input);
        }
        else if(action == Action.Line){
            shapeDraw = new ShapeLine();
            shapeDraw.draw(x1, x2, y1, y2, color, g, input);
        }
        else if (action == Action.Rectangle){
            shapeDraw = new ShapeRectangle();
            shapeDraw.draw(x1, x2, y1, y2, color, g, input);
        }
        else if (action == Action.Triangle){
            shapeDraw = new ShapeTriangle();
            shapeDraw.draw(x1, x2, y1, y2, color, g, input);
        }
        else if (action == Action.Text){
            shapeDraw = new ShapeText();
            shapeDraw.draw(x1, x2, y1, y2, color, g, input);
        }
    }
}
