/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The Super Class for all shape strategy
 */
package Shape;

import java.awt.*;
import User.Action;
import com.alibaba.fastjson.*;

public abstract class ShapeDraw {

    public abstract void draw(int x1, int x2, int y1, int y2, Color color, Graphics g, String input);

    /**
     * Generate the record
     * @return
     */
    public static JSONObject generateRecord(int x1, int x2, int y1, int y2, Color color, Action action, String input){
        JSONObject record = new JSONObject();

        // attach elements
        record.put("startX", x1);
        record.put("startY", y1);
        record.put("endX", x2);
        record.put("endY", y2);
        record.put("color", color);
        record.put("action", action);
        record.put("input", input);

        return record;
    }
}
