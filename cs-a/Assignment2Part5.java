package com.shpp.dmarkov.cs;

/**
 * Created by Denis on 17.05.2016.
 */

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/* Draw optical illusion with matrix of black boxes */
public class Assignment2Part5 extends WindowProgram {
    /* The number of rows and columns in the grid, respectively. */
    /* Program window size */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 400;
    /* Count of rows and cols */
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 6;

    /* The width and height of each box. */
    private static final double BOX_SIZE = 40;

    /* The horizontal and vertical spacing between the boxes. */
    private static final double BOX_SPACING = 10;

    /* Setup coordinates and call drawLines to draw the table */
    public void run() {
        double illustrationWidth = NUM_COLS * BOX_SIZE + (NUM_COLS - 1) * BOX_SPACING;
        double illustrationHight = NUM_ROWS * BOX_SIZE + (NUM_ROWS - 1) * BOX_SPACING;
        double firstX = getWidth() / 2 - illustrationWidth / 2;
        double firstY = getHeight() / 2 - illustrationHight / 2;
        drawLines(firstX, firstY);
    }

    /* Setup coordinate and call drawColls to draw the column */
    private void drawLines(double firstX, double firstY) {
        double x = firstX;
        for (int i = 0; i < NUM_COLS; i++) {
            drawColls(x, firstY);
            x += BOX_SIZE + BOX_SPACING;
        }
    }

    /* Setup coordinate and call drawRectangle to draw colls */
    private void drawColls(double x, double firstY) {
        double y = firstY;
        for (int j = 0; j < NUM_ROWS; j++) {
            drawRectangle(x, y);
            y += BOX_SIZE + BOX_SPACING;
        }
    }

    /* Draw any rectangle */
    private void drawRectangle(double x, double y) {
        GRect rectangle = new GRect(x, y, BOX_SIZE, BOX_SIZE);
        rectangle.setFilled(true);
        rectangle.setColor(Color.black);
        add(rectangle);
    }
}
