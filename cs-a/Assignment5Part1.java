package com.shpp.dmarkov.cs;

import com.shpp.cs.a.console.TextProgram;

/**
 * Created by Denis on 31.05.2016.
 */
public class Assignment5Part1 extends TextProgram {
    public void run() {
        /* Repeatedly prompt the user for a word and print out the estimated
         * number of syllables in that word.
         */
        while (true) {
            String word = readLine("Enter a single word: ");
            println("  Syllable count: " + syllablesIn(word));
        }
    }

    /**
     * Given a word, estimates the number of syllables in that word according to the
     * heuristic specified in the handout.
     *
     * @param word A string containing a single word.
     * @return An estimate of the number of syllables in that word.
     */
    private int syllablesIn(String word) {
        String wordLowerCase = word.toLowerCase(); //set word to lower case
        int wordLength = wordLowerCase.length(); //considers word length
        int syllables = 0; //syllables in word
        char letter; //letter of word
        char previousLetter = 'b'; //previous letter in word, set to 'b' for first letter
        /* iterates letters in the word and counts the number of vowels */
        for(int i = 0; i < wordLength; i++) {
            letter = wordLowerCase.charAt(i); //current letter
            /* increases syllables count when current letter is vowels and previous letter isn't */
            if(isVowels(letter) && !isVowels(previousLetter)) {
                syllables++;
            }
            /* decreases syllables count when last letter is 'e', previous letter isn't vowels
                and syllables value is more then 1
             */
            if(i == (wordLength - 1) && syllables > 1 && letter == 'e' && !isVowels(previousLetter)) {
                syllables--;
            }
            previousLetter = letter; // set previous letter to current value
        }
        return syllables; // return count of syllables in word
    }
    /**
     * Check letter is vovels or not.
     *
     * @param letter char letter/
     * @return true when letter is vowels or false when isn't.
     */
    private boolean isVowels(char letter) {
        String vowels = "aeiouy"; //all vowels letter
        /* iterates chars in the string and when letter and char from vowels string is match return true */
        for(int i = 0; i < vowels.length(); i++) {
            if (letter == vowels.charAt(i)) {
                return true;
            }
        }
        return false; //if not found match for letter in vowels string return false
    }
}
