package ru.nshi.api.schemas;

public class SortingResponse {
    private int[] values;
    private int time;

    public SortingResponse(int[] values, int time) {
        this.values = values;
        this.time   = time;
    }

    public int getTime() {
        return time;
    }

    public int[] getValues() {
        return values;
    }
}
