package com.shpp.dmarkov.cs;

/**
 * Created by U on 28.04.2016.
 */


import com.shpp.karel.KarelTheRobot;

/* Pick beeper in static world */
public class Assignment1Part1 extends KarelTheRobot {
    public void run() throws Exception {
        goToBiper();
        pickBeeper();
        comeBack();
    }

    /* Go to beeper */
    private void goToBiper() throws Exception {
        turnRight();
        move();
        turnLeft();
        while (noBeepersPresent()) {
            move();
        }
    }

    /* Turn right */
    private void turnRight() throws Exception {
        turnLeft();
        turnLeft();
        turnLeft();
    }

    /* Come back to start position */
    private void comeBack() throws Exception {
        turnLeft();
        turnLeft();
        while (frontIsClear()) {
            move();
        }
        turnRight();
        move();
        turnRight();
    }
}
