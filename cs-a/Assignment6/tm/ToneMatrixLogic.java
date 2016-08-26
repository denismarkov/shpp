package com.shpp.dmarkov.cs.assignments.arrays.tm;

import static java.lang.Math.abs;

public class ToneMatrixLogic {
    /**
     * Given the contents of the tone matrix, returns a string of notes that should be played
     * to represent that matrix.
     *
     * @param toneMatrix The contents of the tone matrix.
     * @param column     The column number that is currently being played.
     * @param samples    The sound samples associated with each row.
     * @return A sound sample corresponding to all notes currently being played.
     */
    public static double[] matrixToMusic(boolean[][] toneMatrix, int column, double[][] samples) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];
        for(int row = 0; row < toneMatrix.length; row++) {
            if(toneMatrix[row][column]) {
                for(int i = 0; i < result.length; i++) {
                    result[i] += samples[row][i];
                }
            }
        }
        return normalize(result);
    }

    private static double[] normalize(double[] result) {
        double maxValueInResult = 0.0;
        double maxCorrectValue = 1.0;
        for(int i = 0; i < result.length; i++) {
            if(abs(result[i]) > abs(maxValueInResult)) maxValueInResult = result[i];
        }
        if(abs(maxValueInResult) > maxCorrectValue) {
            for (int i = 0; i < result.length; i++) {
                result[i] = result[i] / maxValueInResult;
            }
        }
        return result;
    }
}
