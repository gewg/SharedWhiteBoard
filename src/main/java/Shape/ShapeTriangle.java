/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The strategy to draw triangle
 */
package Shape;

import java.awt.*;

public class ShapeTriangle extends ShapeDraw{

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
        // distance between two points
        double distance = Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
        // draw triangle
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x1, y1, (int)(x1 - distance), y2);
        g.drawLine((int)(x1 - distance), y2, x2, y2);
    }
}
