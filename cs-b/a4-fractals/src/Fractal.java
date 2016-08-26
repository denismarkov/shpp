import java.awt.*;
import javax.swing.*;

/**
 * Created by Denis on 04.08.2016.
 * Draw Sierpinski Triangle
 */

public class Fractal {
    /* Set triangle side size */
    private final static int TRIANGLE_SIDE_SIZE = 600;
    /* Set recursion deep */
    private final static int RECURSION_DEEP = 5000;
    /* Set borders frame size in part of 1 from all frame size*/
    private final static double BORDERS_FRAME = 0.1;

    public static void main(String[] args) {
        run();
    }
    /* Add JFrame, set it properties and call drawFirstTriangle() to draw Sierpinski Triangle */
    static void run() {
        JFrame frame = new JFrame("Sierpinski Triangle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width;
        int height = width = (int)(TRIANGLE_SIDE_SIZE / (1 - BORDERS_FRAME));
        int recursionDeep =  getRecursionDeep();
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(Color.BLACK);
                drawFirstTriangle(width, height, recursionDeep, g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    /* Get max possible recursion deep
    @return max deep of recursion
     */
    private static int getRecursionDeep() {
        int maxRecursionDeep = 0;
        double minTriangleSideSize = TRIANGLE_SIDE_SIZE  / 2;
        while(minTriangleSideSize > 2) {
            maxRecursionDeep++;
            minTriangleSideSize /= 2;
        }
        return (RECURSION_DEEP <= maxRecursionDeep) ? RECURSION_DEEP : maxRecursionDeep;
    }
    /* Get triangle hight, draw the first triangles and, if recursion deep is bigger then 0, set coordinates for second
    triangle and call drawSubTriangles() for draw it
     */
    private static void drawFirstTriangle(int width, int height, int recursionDeep, Graphics g) {
        int triangleH = (int)(TRIANGLE_SIDE_SIZE * Math.sqrt(3) / 2);
        int x1 = (width - TRIANGLE_SIDE_SIZE) / 2;
        int y1 = height - (height - triangleH) / 2;
        int x2 = width - (width - TRIANGLE_SIDE_SIZE) /2;
        int y2 = height - (height - triangleH) / 2;
        int x3 = width / 2;
        int y3 = (height - triangleH) / 2;
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x1, y1, x3, y3);
        g.drawLine(x2, y2, x3, y3);
        if(recursionDeep > 0) {
            recursionDeep--;
            int newX1 = (x1 + x2) / 2;
            int newY1 = (y1 + y2) / 2;
            int newX2 = (x1 + x3) / 2;
            int newY2 = (y1 + y3) / 2;
            int newX3 = (x2 + x3) / 2;
            int newY3 = (y2 + y3) / 2;
            drawSubTriangles(recursionDeep, newX1, newY1, newX2, newY2, newX3, newY3, g);
        }
    }
    /* Draw triangle on preset coordinates, if recursion level has next - get coordinates for next triangles and
    call itself for draw them
     */
    private static void drawSubTriangles(int recursionDeep, int x1, int y1, int x2, int y2, int x3, int y3, Graphics g) {
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x1, y1, x3, y3);
        g.drawLine(x2, y2, x3, y3);
        if(recursionDeep > 0) {
            recursionDeep--;
            int newX1 = (x1 + x2) / 2 + (x2 - x3) / 2;
            int newY1 = (y1 + y2) / 2 + (y2 - y3) / 2;
            int newX2 = (x1 + x2) / 2 + (x1 - x3) / 2;
            int newY2 = (y1 + y2) / 2 + (y1 - y3) / 2;
            int newX3 = (x1 + x2) / 2;
            int newY3 = (y1 + y2) / 2;
            drawSubTriangles(recursionDeep, newX1, newY1, newX2, newY2, newX3, newY3, g);
            newX1 = (x3 + x2) / 2 + (x2 - x1) / 2;
            newY1 = (y3 + y2) / 2 + (y2 - y1) / 2;
            newX2 = (x3 + x2) / 2 + (x3 - x1) / 2;
            newY2 = (y3 + y2) / 2 + (y3 - y1) / 2;
            newX3 = (x3 + x2) / 2;
            newY3 = (y3 + y2) / 2;
            drawSubTriangles(recursionDeep, newX1, newY1, newX2, newY2, newX3, newY3, g);
            newX1 = (x1 + x3) / 2 + (x3 - x2) / 2;
            newY1 = (y1 + y3) / 2 + (y3 - y2) / 2;
            newX2 = (x1 + x3) / 2 + (x1 - x2) / 2;
            newY2 = (y1 + y3) / 2 + (y1 - y2) / 2;
            newX3 = (x1 + x3) / 2;
            newY3 = (y1 + y3) / 2;
            drawSubTriangles(recursionDeep, newX1, newY1, newX2, newY2, newX3, newY3, g);
        }
    }
}