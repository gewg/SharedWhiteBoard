/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The strategy to draw circle
 */
package Shape;

import java.awt.*;

public class ShapeCircle extends ShapeDraw{

    /**
     * Draw the shape on the canvas
     *
     * @param x1    shape's start x-axis
     * @param x2    shape's end x-axis
     * @param y1    shape's start y-axis
     * @param y2    shape's end y-axis
     * @param color shape's color
     */
    @Override
    public void draw(int x1, int x2, int y1, int y2, Color color, Graphics g, String input) {
        // set color
        g.setColor(color);
        // count the radius
        int radius = Math.min(Math.abs(x1 - x2), Math.abs(y1 - y2));
        // draw circle
        g.drawOval(Math.min(x1, x2), Math.min(y1, y2), radius, radius);
    }
}
