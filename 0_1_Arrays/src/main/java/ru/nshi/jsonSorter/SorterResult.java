package ru.nshi.jsonSorter;

/*
 *      The result of the sort
 */

public class SorterResult {
    public int[]   values;
    public long    time;

    // Empty constructor for serialization
    public SorterResult() {
    }

    public SorterResult(int[] values, long time) {
        this.values = values;
        this.time   = time;
    }
}
