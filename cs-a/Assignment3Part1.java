package com.shpp.dmarkov.cs;

/**
 * Created by Denis on 11.05.2016.
 */

import com.shpp.cs.a.console.TextProgram;

/* Ask user how many he train during this week and comparison it with
    necessary value
 */
public class Assignment3Part1 extends TextProgram {
    /* Minimum length of  Cardiovacular Train */
    private static final int minCardiovacularTrain = 30;
    /* Minimum length of  Blood Train  */
    private static final int minBloodTrain = 40;
    /* Minimum days need to train to cardiovacular health */
    private static final int minCardiovacularTrainDay = 5;
    /* Minimum days need to train to blood health */
    private static final int minBloodTrainDay = 3;
    /* Count day, when user train more then 30 minutes */
    private int cardiovacularTrainDay = 0;
    /* Count day, when user train more then 40 minutes */
    private int bloodTrainDay = 0;

    /* Run program */
    public void run() {
        if (consoleInput()) { // if user input correct data, show result
            consoleResult();
        }
        ;

    }

    /* Read user input and end programm if user input incorrect data */
    private boolean consoleInput() {
        int i;
        for (i = 1; i < 8; i++) {
            int train;
            try {
                train = readInt("How many minutes did you do on day " + i + "? ");
            } catch (Exception e) { // end program if input is incorrect
                println("Your number must be integer, try again");
                break;
            }
            if (train >= minBloodTrain) { // count blood train days
                bloodTrainDay++;
            }
            if (train >= minCardiovacularTrain) { // count Cardiovacular train days
                cardiovacularTrainDay++;
            }

        }
        /* End programm if input is incorrect */
        if (i == 8) {
            return true;
        } else {
            return false;
        }

    }

    /* Compare blood and Cardiovacular train day with minimum need days and show resulr */
    private void consoleResult() {
        println("Cardiovacular health:");
        if (cardiovacularTrainDay >= minCardiovacularTrainDay) {
            println("Great job! You've done enough exercise for cardiovacular health.");
        } else {
            println("You needed to train hard for at least " + (minCardiovacularTrainDay - cardiovacularTrainDay) + " more day(s) a week!");
        }
        println("Blood pressure:");
        if (bloodTrainDay >= minBloodTrainDay) {
            println("Great job! You've done enough exercise to keep a low blood pressure.");
        } else {
            println("You needed to train hard for at least " + (minBloodTrainDay - bloodTrainDay) + " more day(s) a week!");
        }
    }
}
