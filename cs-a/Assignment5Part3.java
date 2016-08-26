package com.shpp.dmarkov.cs;

/**
 * Created by Denis on 05.06.2016.
 */
import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Assignment5Part3 extends TextProgram {
    public void run() {
        /* Get dictionary value from  readDictionary() once,
        * request user for string of 3 letters, print all words, that
         * have this letter in same order*/
        ArrayList<String> dictionary = readDictionary();
        while (true) {
            String userInput = readLine("Enter string of 3 letters:  ");
            printAllWords(userInput, dictionary);
            println();
        }
    }

    /* Get dictionary file from URL, read line from it and assign
        string value to ArrayList cell
        @return ArrayList dictionary
     */
    private ArrayList<String> readDictionary() {
        ArrayList<String> dictionary = new ArrayList<>();
        try {
            /* open file URL */
            URL file = new URL("http://cs.programming.kr.ua/s/a/1604/downloads/en-dictionary.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(file.openStream()));
            /* read line from it and set value to ArrayList dictionary */
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                dictionary.add(line);
            }
            br.close();
        }
        catch (IOException e) {
            println("File not read"); //if something wrong
        }
        return dictionary;
    }
    /* Get user input string, find all words at dictionary, that have letter from user input is same order,
        print all this words.
        @param userInput String of 3 letters from user
        @param ArrayList<String> dictionary - ArrayList of string, that contains possible words
     */
    private void printAllWords(String userInput, ArrayList<String> dictionary) {
        /* Create case insensitive pattern of words with 3 user input letter in user input order */
        Pattern p = Pattern.compile("[^"+ userInput.charAt(1) + userInput.charAt(2) + "]*" +
                userInput.charAt(0) + "[^"+ userInput.charAt(2) + "]*" +
                userInput.charAt(1) + ".*" + userInput.charAt(2) + ".*$", Pattern.CASE_INSENSITIVE);
        /* find words with pattern in dictionary, print all matches */
        for (String word : dictionary) {
            Matcher m = p.matcher(word);
            if(m.matches()) {
                println(m.group());
            }

        }

    }
}