package com.shpp.dmarkov.cs;

/**
 * Created by U on 28.04.2016.
 */

import com.shpp.karel.KarelTheRobot;

/* Fill column */
public class Assignment1Part2 extends KarelTheRobot {
    public void run() throws Exception {
        while (frontIsClear()) { // fill column and go next
            fillColumn();
            comeBack();
            goNext();
        }
        fillColumn(); // fill last column
    }

    /* Fill column beepers */
    private void fillColumn() throws Exception {
        turnLeft();
        while (frontIsClear()) {
            if (noBeepersPresent()) {
                putBeeper();
            }
            move();
        }
        if (noBeepersPresent()) {
            putBeeper();
        }
    }

    /* Go to start line */
    private void comeBack() throws Exception {
        turnAround();
        while (frontIsClear()) {
            move();
        }
        turnLeft();

    }

    /* Turn Karel around */
    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }

    /* Go to next column */
    private void goNext() throws Exception {
        move();
        move();
        move();
        move();
    }
}
