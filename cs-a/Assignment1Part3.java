package com.shpp.dmarkov.cs;

/**
 * Created by U on 28.04.2016.
 */

import com.shpp.karel.KarelTheRobot;

/**
 * Find midpoint of line
 * 1. put beeper
 * 2. move to the wall
 * 3. put beeper
 * 4. Loop
 * 4.1. Turn around
 * 4.2. Move one cell
 * 4.3. Loop (move to beeper, put it, turn around, transfer beeper on one cell to center, make step)
 * 5. Put unnecessary beeper
 */
public class Assignment1Part3 extends KarelTheRobot {
    public void run() throws Exception {
        putBeeper();
        if(frontIsClear()) {
            setBeeperAtEdge(); //put beeper near wall
            moveBeeperToCenter(); // step by step move beepers to the center
            pickBeeper(); // pick redundant beeper
            setKarelToCenter(); // Make last step to center
        }
    }

    /*put beeper near wall*/
    private void setBeeperAtEdge() throws Exception {
        while (frontIsClear()) {
            move();
        }
        putBeeper();
        turnAround();
        move();
    }

    /* Turn around Karel */
    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }

    /* Step by step move beepers to center */
    private void moveBeeperToCenter() throws Exception {
        while (noBeepersPresent()) {
            while (noBeepersPresent()) {
                move();
            }
            pickBeeper();
            turnAround();
            move();
            putBeeper();
            move();
        }
    }

    /* Make last step to center */
    private void setKarelToCenter() throws Exception {
        turnAround();
        move();
    }

}
