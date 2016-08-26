package com.shpp.dmarkov.cs.namesurfer;

/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {


    /* Instance variables for the canvas width and height, and column width */
    double canvasWidth;
    double canvasHeight;
    double columnWidth;
    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    /* Instance variable for the ArrayList of NameSurferEntry instances, which
    * is accessed by addEntry and drawEntries */
    private ArrayList<NameSurferEntry> entries = new ArrayList<>();
    private double horizontalBottomLineY;
    private double horizontalTopLineY;

    public NameSurferGraph() {
        addComponentListener(this);
        // You fill in the rest //
    }


    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        entries.clear();
    }

	
	/* Method: addEntry(entry) */

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        entries.add(entry);
    }


    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        /* Delete all graphical objects from canvas */
        removeAll();

		/* Get updated values for canvas width, height and column width */
        canvasWidth = getWidth();
        canvasHeight = getHeight();
        columnWidth = canvasWidth / NDECADES;

		/* Draw the background (lines and decade labels */
        drawGrid();

		/* Draw the entries */
        drawEntries();
    }

    private void drawGrid() {
        /* Y position of the labels */
        double labelY = canvasHeight - LABEL_PADDING;

		/* Add the labels to the canvas */
        for (int i = 0; i < NDECADES; i++) {

			/* get the decade and convert to a string */
            int intDecade = START_DECADE + (i * 10);
            String stringDecade = Integer.toString(intDecade);

			/* X position of the label */
            double labelX = LABEL_PADDING + (i * columnWidth);

			/* Add the label to the canvas */
            add(new GLabel(stringDecade, labelX, labelY));
        }

		/* Y positions of the horizontal lines */
        double labelHeight = (new GLabel(Integer.toString(START_DECADE))).getAscent();
        horizontalBottomLineY = labelY - labelHeight;
        horizontalTopLineY = canvasHeight - horizontalBottomLineY;

		/* Add the horizontal lines to the canvas */
        add(new GLine(0, horizontalBottomLineY, canvasWidth, horizontalBottomLineY));
        add(new GLine(0, horizontalTopLineY, canvasWidth, horizontalTopLineY));

		/* Add the vertical lines to the canvas */
        for (int i = 0; i <= NDECADES; i++) {

			/* X position of the vertical line */
            double verticalLineX = i * columnWidth;

			/* Add the vertical line to the canvas */
            add(new GLine(verticalLineX, 0, verticalLineX, canvasHeight));
        }
    }

    private void drawEntries() {
        for (NameSurferEntry entry : entries) {

			/* Set the colour for this entry */
            Color color = getColor(entries.indexOf(entry));

			/* The number of pixels per rank required to make the top of the
             * plot area rank 1 and the bottom rank 1000 */
            double heightToRankRatio = (horizontalBottomLineY - horizontalTopLineY) / MAX_RANK;

			/* Variable for the entry's name, to use in labels in the for loop below */
            String name = entry.getName();

			/* Draw the entry's line, one decade at a time */
            for (int i = 0; i < NDECADES - 1; i++) {

				/* Set the Y coordinates of this portion of the line */
                int firstRank = entry.getRank(i);
                int secondRank = entry.getRank(i + 1);
                double firstY;
                double secondY;
                firstY = (firstRank == 0) ? horizontalBottomLineY : (firstRank * heightToRankRatio) + horizontalTopLineY;
                secondY = (secondRank == 0) ? horizontalBottomLineY : (secondRank * heightToRankRatio) + horizontalTopLineY;

				/* Set the X coordinates of this portion of the line */
                double firstX = i * columnWidth;
                double secondX = (i + 1) * columnWidth;

				/* Add the line */
                GLine line = new GLine(firstX, firstY, secondX, secondY);
                line.setColor(color);
                add(line);

				/* Set the entry's label text */
                String entryLabel;
                if (firstRank != 0) {
                    entryLabel = name + " " + Integer.toString(firstRank);
                } else {
                    entryLabel = name + " *";
                }

				/* set the entry's label's position */
                double labelX = firstX + LABEL_PADDING;
                double labelY = firstY - LABEL_PADDING;

				/* add the entry's label */
                GLabel label = new GLabel(entryLabel, labelX, labelY);
                label.setColor(color);
                add(label);

				/* add the final label if this is the last iteration of the for loop */
                if (i + 1 == NDECADES - 1) {
                    labelX = secondX + LABEL_PADDING;
                    labelY = secondY - LABEL_PADDING;
                    if (secondRank != 0) {
                        entryLabel = name + " " + Integer.toString(secondRank);
                    } else {
                        entryLabel = name + " *";
                    }
                    GLabel label2 = new GLabel(entryLabel, labelX, labelY);
                    label2.setColor(color);
                    add(label2);
                }

            }

        }

    }

    /* Method: getColor(indexOf) */

    /**
     * Returns a colour in a set order, cycling through this according to the entry's
     * index in the ArrayList (as passed through as parameter)
     */
    private Color getColor(int indexOf) {
        if (indexOf == 0 || indexOf % 4 == 0) {
            return Color.blue;
        } else if (indexOf == 1 || indexOf % 4 == 1) {
            return Color.red;
        } else if (indexOf == 2 || indexOf % 4 == 2) {
            return Color.magenta;
        } else {
            return Color.black;
        }
    }

    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}
