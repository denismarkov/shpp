package com.shpp.dmarkov.cs;

/**
 * Created by Denis on 18.05.2016.
 */

import acm.graphics.GOval;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/* Draw tracks */
public class Assignment2Part6 extends WindowProgram {
    /* Count of circles */
    private static final double QUANTITY = 10;
    /* Circles diameter */
    private static final double DIAMETER = 100;
    /* Offset next circle in % of circle radius */
    private static final double OFFSET_X = 0.58;
    private static final double OFFSET_Y = 0.42;

    /* Setup circle coordinate and calls alternately drawBottomCircles() and drawUpCircles() to
        draw bottom and up circles
     */
    public void run() {
        int x = 0; //x coordinate of circle
        int y = 0; // y coordinate of circle
        double bottomY = DIAMETER * OFFSET_Y; //offset bottom circles
        double SHIFT = DIAMETER * OFFSET_X; // offset next circles
        for (int i = 0; i < QUANTITY; i++) {
            if (i % 2 == 0) {
                drawBottomCircles(x, bottomY);
                x += SHIFT;
            } else {
                drawUpCircles(x, y);
                x += SHIFT;
            }
        }


    }

    /* Draw bottom circle */
    private void drawBottomCircles(double x, double bottomY) {
        GOval BottomCircle = new GOval(x, bottomY, DIAMETER, DIAMETER);
        BottomCircle.setColor(Color.RED);
        BottomCircle.setFilled(true);
        BottomCircle.setFillColor(Color.GREEN);
        add(BottomCircle);

    }

    /* Draw up circle */
    private void drawUpCircles(double x, double y) {
        GOval UpCircle = new GOval(x, y, DIAMETER, DIAMETER);
        UpCircle.setColor(Color.RED);
        UpCircle.setFilled(true);
        UpCircle.setFillColor(Color.GREEN);
        add(UpCircle);
    }
}
