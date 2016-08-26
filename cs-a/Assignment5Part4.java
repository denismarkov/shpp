package com.shpp.dmarkov.cs;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Denis on 05.06.2016.
 */
public class Assignment5Part4 extends TextProgram {
    String reg = "\"";
    public void run() {
        /* Find column whith columnIndex and print value from it */
        /* Set path to CSV file */
        final String filename = "C:\\Users\\Denis\\IdeaProjects\\Problem_Solving\\src\\com\\shpp\\dmarkov\\cs\\test_csv.csv";
        /* Set column to export index */
        int columnIndex = 2;
        ArrayList<String> resultColumn = extractColumn(filename, columnIndex);
        println(resultColumn);
    }

    /* Get filename and columnIndex, call readFile() to get value from file, export
        it value to column, return column with need index
        @param filename - string path to file
        @param columnIndex - int index of need column
        return result column - ArrayList of column value
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {
        ArrayList<String> file = readFile(filename);
        ArrayList<ArrayList<String>> lines = new ArrayList<>();
        /* foreach line in file, extract CSV to ArrayList */
        for (String line : file) {
            ArrayList<String> column = fieldsIn(line);
            lines.add(column);
        }
        /* foreach lines and get value from preset column */
        ArrayList<String> resultColumn = new ArrayList<>();
        for(ArrayList<String> arrayLine : lines) {
            String row = arrayLine.get(columnIndex);
            resultColumn.add(row);
        }
        return resultColumn;
    }
    /* Read file and write value of it lines to ArrayList<String> file
        @param filename - string path to file
        @return file - ArrayList with value of file lines
     */
    private ArrayList<String> readFile(String filename) {
        ArrayList<String> file = new ArrayList<>();
        try {
            /* open file */
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while (true) {
                /* read lines in file */
                String line = br.readLine();
                if (line == null) break;
                file.add(line);
            }
            br.close(); // close file
        } catch (IOException e) {
            println("File not read"); //something wrong
        }
        return file;
    }

    /* Get file line, find separating char and extract separate value to ArrayList
        @param line - string with CSV
        @return columns - ArrayList with value of CSV line
     */
    private ArrayList<String> fieldsIn(String line) {
        ArrayList<String> columns = new ArrayList<>();
        int endColumnIndex; //index of ',', use for separate data
        while (true) {

            /* Ignore ',' char when data in "",
            * get value between "", extract it,
            * create new string without extract value
            * */

            if (line.startsWith("\"")) {
                endColumnIndex = (line.contains("\",")) ? (line.indexOf("\",") + 1) : line.length();
                addColumn(line, endColumnIndex, columns);
                if(endColumnIndex == line.length()) {
                    break;
                }
                line = line.substring(endColumnIndex + 1, line.length());
                continue;
            }
            /* if data have ',' char - extract value before ',' and create new string without extract value */
            if (line.contains(",")) {
                endColumnIndex = line.indexOf(',');
                addColumn(line, endColumnIndex, columns);
                line = line.substring(endColumnIndex + 1, line.length());
            } else {
                endColumnIndex = line.length();
                addColumn(line, endColumnIndex, columns);
                break;
            }
        }
        return columns;
    }

    /* Get string, end substring index, ArrayList columns, return substring whit value of string line between this index
     */
    private void addColumn(String line, int endColumnIndex, ArrayList<String> columns) {
        String column = line.substring(0, endColumnIndex);
        /* Treads line with quotes */
        if(column.contains("\"")) {
            column = column.substring(1, column.length() - 1);
            column = column.replace("\"\"", "\"");
        }
        columns.add(column);
    }

}
