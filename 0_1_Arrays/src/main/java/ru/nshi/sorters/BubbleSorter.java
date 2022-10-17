package ru.nshi.sorters;

/*
 *  Bubble sort. That's the one where we swap neighbored elements until the array happens to be sorted.
 *
 *  Actually, here we go, an example:
 *
 *  Input: array[] = { 5, 1, 4, 2, 8 }
 *
 *  Bubble sort starts with first two elements, comparing them to check which one is greater
 *  ( 5 1 4 2 8 ) -> ( 1 5 4 2 8 ), Here, algorithm compares the first two elements, and swaps since 5 > 1
 *  ( 1 5 4 2 8 ) -> ( 1 4 5 2 8 ), Swap since 5 > 4
 *  ( 1 4 5 2 8 ) –> ( 1 4 2 5 8 ), Swap since 5 > 2
 *  ( 1 4 2 5 8 ) –> ( 1 4 2 5 8 ), Now, since these elements are already in order (8 > 5), algorithm does not swap them.
 *
 *  Time complexety - O(n^2)
 *  Space complexety - O(1) (only need extra memory to swap elements)
 */

public class BubbleSorter implements Sorter {

    @Override
    public void sort(int[] source) {
        // The array is sorted when the number of swaps is 0
        while (iterate(source) != 0);

        // Array is sorted
    }

    /**
     * Supplied array is modified.
     * @returns How many times did we swap the elements of the source array
     */
    public int iterate(int[] source) {
        int swaps = 0;
        for (int i = 0; i < source.length - 1; i++) {
            int first   = source[i];
            int second  = source[i + 1];

            if (first > second) {
                swaps++;

                // Swap first and second elements
                source[i]       = second;
                source[i + 1]   = first;
            }
        }

        return swaps;
    }
}
