package com.shpp.dmarkov.cs;

/**
 * Created by Denis on 12.05.2016.
 */

import com.shpp.cs.a.console.TextProgram;

/* Ask user for number and if it even - take half, if not even - make 3n + 1 while number not = 1 */
public class Assignment3Part2 extends TextProgram {
    /* Get user input and run program if it is correct */
    public void run() {
        int n = 0; // user number
        try {
            n = readInt("Enter a number: "); // read user number
        } catch (Exception e) {
            println("Your number must be a positive and integer");
        }
        consoleResult(n);
    }

    /* Conduct operations on number and print it */
    private void consoleResult(int n) {
        if (n >= 0) { // validation n
            while (n != 1) {
                if (n < 0) {
                    println("Your number is too large");
                    break;
                }
                if (isEven(n)) {
                    int tempN = n;
                    n = n / 2;
                    println(tempN + " is even so I take half: " + n);
                } else {
                    int tempN = n;
                    n = 3 * n + 1;
                    println(tempN + " is odd so I make 3n + 1: " + n);
                }
            }
        } else {
            println("Your number must be a positive and integer");
        }
    }

    /* Validate n is even and not 0 */
    private boolean isEven(int n) {
        if (n % 2 == 0 && n != 0) {
            return true;
        } else {
            return false;
        }
    }
}
