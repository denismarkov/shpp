package com.shpp.dmarkov.cs;

/**
 * Created by Denis on 19.05.2016.
 */

import acm.util.RandomGenerator;
import com.shpp.cs.a.console.TextProgram;

/* Simulates st. Peterburg game */
public class Assignment3Part5 extends TextProgram {
    /* Minimum win sum */
    private static final int stopSum = 20;

    public void run() {
        while (consoleInput()) { // start new game after each user input
            int startSum = 1; // Start game sun
            int totalSum = 1; // user win sum
            int gamesCount = 0; // count games
            while (totalSum < stopSum) { // continue game while user win sum less than minimum win
                totalSum += game(startSum);
                gamesCount++;
                println("Your total is $" + totalSum);
            }
            println("It took " + gamesCount + " games to earn $20");
        }
    }

    /* Start new game when user input '1' and end game if user enter something else */
    private boolean consoleInput() {
        int userInput = 0;
        try {
            userInput = readInt("Enter '1' to start a new game or '2' to exit: ");
        } catch (Exception e) {
            println("Your input is incorrect");
        }
        if (userInput == 1) {
            return true;
        } else {
            return false;
        }
    }

    /* Double sum or end game with probability 0.5 end print game result */
    private int game(int sum) {
        RandomGenerator rgen = RandomGenerator.getInstance();
        while (rgen.nextBoolean()) {
            sum = sum * 2;
        }
        println("This game, you earned $" + sum);
        return sum;

    }

}
