package com.shpp.dmarkov.cs;

/**
 * Created by Всеволодовна on 30.04.2016.
 */

import com.shpp.karel.KarelTheRobot;

/* Fill checkerboard with beepers,
    checkerboard must be a rectangle,
    without beepers adn block
 */
public class Assignment1Part4 extends KarelTheRobot {
    public void run() throws Exception {
        putBeeper(); // put beeper in south east cell
        if (frontIsBlocked()) { // check for width board is more than 1
            turnLeft();
        }
        fillBoard(); // fill board beepers
    }

    /* put beepers in line until line ends, call nextLine() to go to next line */
    private void fillBoard() throws Exception {
        while (frontIsClear()) {
            /* Move one cell if beeper present */
            if (beepersPresent()) {
                move();
            }
            /* Check that front is clear,  move forward, put beeper, if front blocked after it, go to next line */
            if (frontIsClear()) {
                move();
                putBeeper();
                if (frontIsBlocked()) {
                    nextLine();
                }
            }
            /* If front blocked - try to go next line and putt beeper at first cell of line */
            else {
                nextLine();
                putBeeper();
            }
        }
    }

    /* Go to next line */
    private void nextLine() throws Exception {
        if (facingWest()) {
            turnRight();
            if (frontIsClear()) {
                move();
                turnRight();
            }
        } else {
            turnLeft();
            if (frontIsClear()) {
                move();
                turnLeft();
            }
        }
    }

    /* Turn right */
    private void turnRight() throws Exception {
        turnLeft();
        turnLeft();
        turnLeft();
    }
}
