package com.shpp.dmarkov.cs;

/**
 * Created by Denis on 19.05.2016.
 */

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/* Draw pyramid */
public class Assignment3Part4 extends WindowProgram {
    /* Brick height */
    private static final double BRICK_HEIGHT = 20.0;
    /* Brick width */
    private static final double BRICK_WIDTH = 60.0;
    /* Bricks in base line */
    private static final int BRICKS_IN_BASE = 10;

    /* Setup bricks line coordinate and call drawLine to draw bricks line */
    public void run() {
        int bricksInLine = BRICKS_IN_BASE;
        double y = getHeight() - BRICK_HEIGHT;
        for (int i = 0; i < BRICKS_IN_BASE; i++) {
            drawLine(y, bricksInLine);
            bricksInLine--;
            y -= BRICK_HEIGHT;
        }
    }

    /* Setup brick coordinate and call drawLine to draw brick */
    private void drawLine(double y, int bricksInLine) {
        double centerX = getWidth() / 2;
        double lineWidth = bricksInLine * BRICK_WIDTH;
        double x = centerX - (lineWidth / 2);
        for (int i = 0; i < bricksInLine; i++) {
            drawBrick(x, y);
            x += BRICK_WIDTH;
        }

    }

    /* Draw brick */
    private void drawBrick(double x, double y) {
        GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);

        brick.setColor(Color.BLACK);
        brick.setFilled(true);
        brick.setFillColor(Color.YELLOW);
        add(brick);
    }
}
