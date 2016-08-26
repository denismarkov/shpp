package com.shpp.dmarkov.cs.namesurfer;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class NameSurferDataBase implements NameSurferConstants {

	/* Constructor: NameSurferDataBase(filename) */

    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.  The constructor throws an error
     * exception if the requested file does not exist or if an error
     * occurs as the file is being read.
     */
    private BufferedReader br;
    private HashMap<String, NameSurferEntry> namesHashMap;

    public NameSurferDataBase(String filename) {
        namesHashMap = new HashMap<>();

        try {
            br = new BufferedReader(new FileReader(filename));
            while (true) {
                String line = br.readLine();
                if (line != null) {
                    NameSurferEntry entry = new NameSurferEntry(line);
                    String key = entry.getName().toLowerCase();
                    namesHashMap.put(key, entry);
                } else {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("File not read"); //something wrong
        }
    }

	/* Method: findEntry(name) */

    /**
     * Returns the NameSurferEntry associated with this name, if one
     * exists.  If the name does not appear in the database, this
     * method returns null.
     */
    public NameSurferEntry findEntry(String name) {
        // You need to turn this stub into a real implementation //
        return namesHashMap.get(name.toLowerCase());
    }

}

