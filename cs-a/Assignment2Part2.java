package com.shpp.dmarkov.cs;

/**
 * Created by Denis on 03.05.2016.
 */

import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/* Draw illusory conturs picture */
public class Assignment2Part2 extends WindowProgram {
    /* Program window width */
    public static final int APPLICATION_WIDTH = 300;
    /* Program window height */
    public static final int APPLICATION_HEIGHT = 300;

    /* Add 4 circles at the corners and recnatgle in the middle */
    public void run() {
        double radius = getWidth() / 6; // set round radius as 1/6 of program window width
        addLeftTopCircle(radius);
        addRightTopCircle(radius);
        addLeftBottomCircle(radius);
        addRightBottomCircle(radius);
        addRectangle(radius);
    }

    /* Add left top circle */
    private void addLeftTopCircle(double radius) {
        double x = getWidth() - getWidth(); // x coordinate
        double y = getHeight() - getHeight(); //y coordinate
        drawCircle(radius, x, y);
    }

    /* Draw any circle */
    private void drawCircle(double radius, double x, double y) {
        GOval circle = new GOval(x, y, 2 * radius, 2 * radius);
        circle.setColor(Color.GRAY);
        circle.setFilled(true);
        add(circle);
    }

    /* Add right top circle */
    private void addRightTopCircle(double radius) {
        double x = getWidth() - 2 * radius;
        double y = getHeight() - getHeight();
        drawCircle(radius, x, y);
    }

    /* Add left bottom circle */
    private void addLeftBottomCircle(double radius) {
        double x = getWidth() - getWidth();
        double y = getHeight() - 2 * radius;
        drawCircle(radius, x, y);
    }

    /* Add right bottom circle */
    private void addRightBottomCircle(double radius) {
        double x = getWidth() - 2 * radius;
        double y = getHeight() - 2 * radius;
        drawCircle(radius, x, y);
    }

    /* Add rectangle */
    private void addRectangle(double radius) {
        double x = getWidth() - (getWidth() - radius);
        double y = getHeight() - (getHeight() - radius);
        double width = getWidth() - 2 * radius;
        double height = getHeight() - 2 * radius;
        drawRectangle(x, y, width, height);
    }

    /* Draw any rectangle */
    private void drawRectangle(double x, double y, double width, double height) {
        GRect rectangle = new GRect(x, y, width, height);
        rectangle.setFilled(true);
        rectangle.setColor(Color.WHITE);
        add(rectangle);
    }
}

