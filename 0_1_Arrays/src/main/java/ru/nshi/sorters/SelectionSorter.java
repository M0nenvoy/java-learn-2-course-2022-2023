package ru.nshi.sorters;

/*
 *  Selection sort. That's the one where we find the (first) minimum element of an array and insert
 *  it in the beginning of the arary.
 *
 *  An example:
 *
 *  Input: array[] = { 64, 25, 12, 22, 11 };
 *
 *  For the first pass, we traverse the whole array from index 0 to 4.
 *  We find out that the minimum element is clearly 11. We swap it with the element at index 0
 *  ( 64, 25, 12, 22, 11 ) -> ( 11, 25, 12, 22, 64 );
 *
 *  Now, we traverse the array from index 1 to 4, find out that the minimum element is 12,
 *  swap it with the element at index 1 and so forth.
 *
 *  Time complexety - O(n^2)
 *  Space complexety - O(1) (extra memory to swap elements)
 *
 */

public class SelectionSorter implements Sorter {

    @Override
    public void sort(int[] source) {
        int start = 0;
        int end   = source.length;

        // We can stop iterating when start meets with the end. Array should be sorted
        while (start < end) {
            iterate(source, start, end);
            start++;
        }
    }

    public void iterate(int [] source, int start, int end) {
        // Swap the minimum element with the start element
        int minId = minimum(source, start, end);

        int tmp = source[start];
        source[start] = source[minId];
        source[minId] = tmp;
    }

    /// Find the index of the (first) minimum element in the array searching from start to end.
    public int minimum(int[] source, int start, int end) {
        // Minimum element so far
        int min = source[start];

        // The index of the minimum element so far
        int minIndex = start;
        for (int i = start; i < end; i++) {
            if (min > source[i]) {
                min = source[i];
                minIndex = i;
            }
        }

        return minIndex;
    }
}
