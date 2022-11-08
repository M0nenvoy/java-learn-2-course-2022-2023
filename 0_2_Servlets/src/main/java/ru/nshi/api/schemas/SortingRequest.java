package ru.nshi.api.schemas;

/*
 *      Note that we specify sorting algorithm via query params.
 *      That's why it's not present here.
 */
public class SortingRequest {
    private int[] values;

    public SortingRequest() {
    }

    public int[] getValues() {
        return values;
    }
}
