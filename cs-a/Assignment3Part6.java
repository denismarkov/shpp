package com.shpp.dmarkov.cs;

import acm.graphics.GOval;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * Created by Denis on 23.05.2016.
 */
/* Make animation planet move around the sun */
public class Assignment3Part6 extends WindowProgram {
    /* Radius of big star */
    private static final double SUN_RADIUS = 75.0;
    /* Radius of planet */
    private static final double PLANET_RADIUS = 25.0;
    /* Distance between planet and big star */
    private static final double ORBIT_DISTANCE = 50.0;
    /* Small star radius */
    private static final double STARS_RADIUS = 2.0;
    /* Step of planet motion */
    private static final double PLANET_MOTION_STEP = 0.01;
    /* Stars count */
    private static final int COUNT_STARS = 15;
    /* Pause before rendering next frame */
    private static final int PAUSE_TIME = 10;
    /* Color of background */
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    /* Color of planet */
    private static final Color PLANET_COLOR = Color.red;
    /* Color of big star */
    private static final Color SUN_COLOR = Color.orange;
    /* Color of small stars */
    private static final Color STARS_COLOR = Color.orange;

    public void run() {
        setBackground(BACKGROUND_COLOR); // set backgroun color
        GOval sun = drawSun(); // draw big star in the middle of program window
        GOval planet = drawPlanet(); // draw planet on the orbit of big star
        addStars(); // add small stars
        add(planet); // add planet
        add(sun); // add big star
        circleMove(planet); // move planet around the star
    }

    /* setup sun coordinates and call makeCircle method */
    private GOval drawSun() {
        double sunX = getWidth() / 2 - SUN_RADIUS;
        double sunY = getHeight() / 2 - SUN_RADIUS;
        return makeCircle(sunX, sunY, 2 * SUN_RADIUS, 2 * SUN_RADIUS, SUN_COLOR);
    }

    /* setup planet coordinates and call makeCircle method */
    private GOval drawPlanet() {
        double planetX = getWidth() / 2 - PLANET_RADIUS;
        double planetY = getHeight() / 2 - SUN_RADIUS - ORBIT_DISTANCE - PLANET_RADIUS;
        return makeCircle(planetX, planetY, 2 * PLANET_RADIUS, 2 * PLANET_RADIUS, PLANET_COLOR);
    }

    /* Draw any circles */
    private GOval makeCircle(double x, double y, double width, double height, Color color) {
        GOval circle = new GOval(x, y, width, height);
        circle.setFilled(true);
        circle.setFillColor(color);
        return circle;
    }

    /* Generate  random small stars coordinates and draw it */
    private void addStars() {
        for (int i = 0; i < COUNT_STARS; i++) {
            RandomGenerator random = new RandomGenerator();
            double starX = random.nextInt(0, getWidth());
            double starY = random.nextInt(0, getHeight());
            GOval star = makeCircle(starX, starY, 2 * STARS_RADIUS, 2 * STARS_RADIUS, STARS_COLOR);
            add(star);
        }
    }

    /* Move planet around the sun */
    private void circleMove(GOval circle) {
        double time = 0; // planet motion
        double tempX = getWidth() / 2; // previous planet X coordinate
        double tempY = getHeight() / 2 - SUN_RADIUS - ORBIT_DISTANCE; // previous planet Y coordinate
        while (true) {
            time += PLANET_MOTION_STEP;
            /* Get next planet coordinate with circle formula (formula was found in internet)*/
            double planetX = getWidth() / 2 + Math.round((2 * SUN_RADIUS + ORBIT_DISTANCE) * Math.cos(time));
            double planetY = getHeight() / 2 + Math.round((2 * SUN_RADIUS + ORBIT_DISTANCE) * Math.sin(time));
            double dX = planetX - tempX;
            double dY = planetY - tempY;
            tempX = planetX;
            tempY = planetY;
            circle.move(dX, dY);
            pause(PAUSE_TIME);
        }
    }

}
