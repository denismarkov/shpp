package com.shpp.dmarkov.cs;

/**
 * Created by Denis on 19.05.2016.
 */

import com.shpp.cs.a.console.TextProgram;

/* Raise to power */
public class Assignment3Part3 extends TextProgram {
    public void run() {
        /* Base number */
        double base = 5;
        /* Exponent */
        int exponent = -3;
        println(raiseToPower(base, exponent));
    }

    /* Raise base number to power exponent */
    private double raiseToPower(double base, int exponent) {
        if (exponent == 0) { // check for 0 exponent
            return 1;
        }
        if (exponent > 0) {
            double result = base;
            for (int i = 1; i < exponent; i++) {
                result = result * base;
            }
            return result;
        } else {
            double result = base;
            for (int i = -1; i > exponent; i--) {
                result = result * base;
            }
            result = 1 / result;
            return result;
        }
    }
}
