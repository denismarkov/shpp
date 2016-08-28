package com.dmarkov.shpp.csb.task1.Main;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Denis on 23.06.2016.
 * Get from user console input math expression and variable
 */
class ConsoleInput {
    /*
    Get user expression
    @return String user math expression
     */
    static String getUserExpression() throws IOException {
        System.out.println("Support operation is: + - * / ^ sqrt(), sin(), cos(), tan(); variables like: x, y, a, b... Input expression:");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    /*
    Get variables and their value and add it to HashMap userVariables
    @return HashMap variables
     */
    static MyHashMap<String, Double> getUserVariables() throws Exception {
        MyHashMap<String, Double> userVariables = new MyHashMap<>();
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.print("Input variable name, or '1' for exit: ");
            String variablesString = in.nextLine();
            if (variablesString.equals("1")) {
                break;
            }
            System.out.print("Input double variable value (like 10,0): ");
            double value;
            try {
                value = in.nextDouble();
            } catch (Exception e) {
                throw new Exception("Incorrect variable value");
            }

            userVariables.put(variablesString, value);
        }
        return userVariables;
    }
}
