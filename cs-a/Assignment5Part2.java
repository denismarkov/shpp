package com.shpp.dmarkov.cs;

/**
 * Created by Denis on 01.06.2016.
 */
import com.shpp.cs.a.console.TextProgram;
public class Assignment5Part2 extends TextProgram{
    private final int CONVERT_NUMBER = 48; //difference between char code number and int number
    private final int NEXT_RANK_CONDITION = 10; //condition number has more 1 rank
    private final String ERROR = "Error, you input non-numeral symbol "; //print, when something wrong
    private final int MINNUMERAL = 0; // min numeral
    private final int MAXNUMERAL = 9; // max numeral
    private final char QUOTE = '"'; // symbol QUOTE

    public void run() {
        /* Sit in a loop, reading numbers and adding them. */
        while (true) {
            String n1 = readLine("Enter first number:  ");
            String n2 = readLine("Enter second number: ");
            String result = addNumericStrings(n1, n2);
            if(result.contains(ERROR)) {
                println(result);
            } else {
                println(n1 + " + " + n2 + " = " + result);
            }
            println();
        }
    }

    /**
     * Given two string representations of nonnegative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */
    private String addNumericStrings(String n1, String n2) {
        String result = ""; //result string sum of 2 numbers
        char temp = '0'; //temporary value
        /* Check string length, assigns the value of shorter string to shorterN */
        String shorterN = (n1.length() >= n2.length()) ? n2 : n1;
        /* Check string length, assigns the value of longer string to longerN */
        String longerN = (n1.length() < n2.length()) ? n2 : n1;
        int j = shorterN.length() - 1; //index of shorterN string
        /* Iterate value in shorterN and longerN, begin from last char,
            sum int value of char at shorterN and longerN, set temporary
            value temp for sum number > 9, transfer temp value to next rank result string
         */
        for (int i = (longerN.length() - 1); i >= 0; i--) {
            char n1Char = longerN.charAt(i);
            char n2Char;
            if (i > (longerN.length() - shorterN.length() - 1)) {
                n2Char = shorterN.charAt(j);
                j--;
            }
            else {
                n2Char = '0'; //set n2Char to 0 when shorter string end
            }
            String sumChar = sumChar(n1Char, n2Char, temp);
            if(sumChar.contains(ERROR)) {
                return sumChar;
            }
            temp = '0'; //reset temp value
            /* for sum value char > 9 - use only last numeral, first transfer to next rank result sum */
            if(sumChar.length() > 1) {
                result = sumChar.charAt(sumChar.length() - 1) + result;
                temp = sumChar.charAt(0);
            }
            else {
                result = sumChar + result;
            }

        }
        /* if after all iterations temp value not 0 add it to begin result sting */
        if (temp != '0') {
            result = temp + result;
        }
        return result; //return a string representation of n1 + n2
    }
    /**
     * Given two char from string, representations of nonnegative integers,
     * and temp char, adds the
     * numbers represented by those chars and returns the result.
     *
     * @param n1Char The first number.
     * @param n2Char The second number.
     * @param temp The temporary value of previous operation number next rank.
     * @return A String representation of n1 + n2 + temp
     */
    private String sumChar(char n1Char, char n2Char, char temp) {
        String result = "";
        /* convert char to int value */
        int n1Value = n1Char - CONVERT_NUMBER;
        int n2Value = n2Char - CONVERT_NUMBER;
        if(n1Value < MINNUMERAL || n1Value > MAXNUMERAL) {
            return ERROR + QUOTE + n1Char + QUOTE;
        }
        if(n2Value < MINNUMERAL || n2Value > MAXNUMERAL) {
            return ERROR + QUOTE + n2Char + QUOTE;
        }
        int tempValue = temp - CONVERT_NUMBER;
        int sum = n1Value + n2Value + tempValue;
        if (sum < NEXT_RANK_CONDITION) {
            return result + (char)(sum + CONVERT_NUMBER);
        }
        char number = (char)(sum - NEXT_RANK_CONDITION + CONVERT_NUMBER);
        return result + '1' + number;
    }
}
