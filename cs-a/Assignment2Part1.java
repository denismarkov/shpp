package com.shpp.dmarkov.cs;

/**
 * Created by Denis on 03.05.2016.
 */
/* Found quadratic equation roots for user input equation coefficients */

import com.shpp.cs.a.console.TextProgram;

public class Assignment2Part1 extends TextProgram {
    /* Initialize variable
    a, b, c - user input,
    D - discriminant,
    x1, x2 - roots
     */
    private double a, b, c;

    /* Check user input is correct and run program */
    public void run() {
        try {
            input();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            calculation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Read user input */
    private void input() throws Exception {
        a = readDouble("Please enter a: ");
        b = readDouble("Please enter b: ");
        c = readDouble("Please enter c: ");

    }

    /* Calculation roots and print it */
    private void calculation() throws Exception {
        if (a != 0) {
            double D = (b * b) - (4 * a * c);
            double sqrtD = Math.sqrt(D);
            if (D > 0) {
                double x1 = (-b - sqrtD) / (2 * a);
                double x2 = (-b + sqrtD) / (2 * a);
                System.out.println("There are two roots: " + x1 + " and " + x2);
            }
            if (D == 0) {
                double x1 = -b / (2 * a);
                System.out.println("There is one root: " + x1);
            }
            if (D < 0) {
                System.out.println("There are no real roots");
            }
        }

    }
}
