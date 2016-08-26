package com.shpp.dmarkov.cs;

/**
 * Created by Denis on 13.05.2016.
 */

import acm.graphics.GLabel;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/* Draw vertical tricolor flag */
public class Assignment2Part4 extends WindowProgram {
    /* Flag stripe width */
    private static final int STRIPE_WIDTH = 200;
    /* Flag stripe height */
    private static final int STRIPE_HEIGHT = 350;
    /* Correct window getHeight() */
    private static final int MARGIN = 23;
    /* Stripes colors */
    private static final Color FIRST_STRIPE_COLOR = Color.BLACK;
    private static final Color SECOND_STRIPE_COLOR = Color.YELLOW;
    private static final Color THIRD_STRIPE_COLOR = Color.RED;
    /* Flag label */
    private static final String LABEL = "Flag of Belgium";

    public void run() {
        drawFlag(); // draw vertical flag with 3 stripes
        setLabel(); // set label to flag
    }

    /* Set stripe coordinate and color and call drawStripe() to draw it */
    private void drawFlag() {
        int flagWidth = STRIPE_WIDTH * 3;
        double startPointX = getWidth() / 2 - flagWidth / 2;
        double startPointY = getHeight() / 2 - STRIPE_HEIGHT / 2;
        double secondStripeX = startPointX + STRIPE_WIDTH;
        double thirdStripeX = secondStripeX + STRIPE_WIDTH;
        drawStripe(startPointX, startPointY, FIRST_STRIPE_COLOR);
        drawStripe(secondStripeX, startPointY, SECOND_STRIPE_COLOR);
        drawStripe(thirdStripeX, startPointY, THIRD_STRIPE_COLOR);
    }

    /* Draw flag stripe */
    private void drawStripe(double stripeX, double stripeY, Color color) {
        GRect rectangle = new GRect(stripeX, stripeY, STRIPE_WIDTH, STRIPE_HEIGHT);
        rectangle.setFilled(true);
        rectangle.setColor(color);
        add(rectangle);
    }

    /* Set flag label */
    private void setLabel() {
        GLabel country = new GLabel(LABEL);
        country.setFont("London-36");
        country.setColor(Color.BLACK);
        add(country, (getWidth() - country.getWidth()), (getHeight() - country.getAscent() + MARGIN));
    }
}

