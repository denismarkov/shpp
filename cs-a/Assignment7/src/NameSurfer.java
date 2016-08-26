package com.shpp.dmarkov.cs.namesurfer;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */


import com.shpp.cs.a.simple.SimpleProgram;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {

	/* Method: init() */

    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */
    private JTextField nameField;
    private JButton graphButton, clearButton;
    private NameSurferGraph graph;
    private NameSurferDataBase namesDataBase;

    public void init() {
        // You fill this in, along with any helper methods //
        add(new JLabel("Name: "), NORTH);
        nameField = new JTextField(30);
        nameField.setActionCommand("Graph");
        graphButton = new JButton("Graph");
        clearButton = new JButton("Clear");
        add(nameField, NORTH);
        add(graphButton, NORTH);
        add(clearButton, NORTH);
        nameField.addActionListener(this);
        addActionListeners();
        namesDataBase = new NameSurferDataBase(NAMES_DATA_FILE);
        graph = new NameSurferGraph();
        add(graph, CENTER);
    }

	/* Method: actionPerformed(e) */

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Graph")) {
            String name = nameField.getText();
            NameSurferEntry entry = namesDataBase.findEntry(name);
            if (entry != null) {
                graph.addEntry(entry);
                graph.update();
            }
        } else if (cmd.equals("Clear")) {
            graph.clear();
            graph.update();
        }
    }

}
