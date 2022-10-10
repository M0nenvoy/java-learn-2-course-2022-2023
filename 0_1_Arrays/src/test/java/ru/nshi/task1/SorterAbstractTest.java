package ru.nshi.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.nshi.Util;
import ru.nshi.sorterWrapper.SorterWrapper;
import ru.nshi.sorters.DummySorter;

/**
 *      Lmao, let's use inheritance to write tests. We will create an abstract SorterTest (well, this one)
 *      write all the tests we need here. And then we will be creating the children for this abstract test
 *      specifing the sorter algorithm that we need.
 *
 *      Edit: it actually worked
 */

public abstract class SorterAbstractTest {

    protected final int[] ARRAY_TO_SORT = { 4, 2, 3, 5, 6, 12, 0, -1, 9 };
    protected final int[] [] ARRAYS_TO_SORT = {
        ARRAY_TO_SORT,
        { 1, 2, 3, 4 },
        { 1, 4, 3, 2 },
        { 5, 4, 3, 2, 12, 8, 90, -10, 0, -10, 0, 0, 3 },
    };

    // Should be overritten
    protected SorterWrapper getSorterWrapper() {
        return new SorterWrapper(new DummySorter());
    }

     /*
      *     Upon getting an empty array as an argument we should return the copy of an empty array
      */
    @Test
    public void emptyArrayReturnedTest() throws Exception {
        int[] arrayPassed   = {};
        int[] arrayReturned = this.getSorterWrapper().sort(arrayPassed);

        Assertions.assertTrue(arrayReturned.length == 0);
    }

    /*
     *      An array passed should not be modified
     */
    @Test
    public void arrayNotModified() throws Exception {
        // Array in it's initial state
        int[] initial = ARRAY_TO_SORT;

        // Array after it's been passed to a function
        int[] passed = initial;
        initial = initial.clone();

        this.getSorterWrapper().sort(passed);

        // Assert that array in it's initial state an array that's been passed to a function are equal.
        Assertions.assertArrayEquals(initial, passed);
    }

    /*
     *      Check if the sorted array and the array passed have the same length
     */
    @Test
    public void sizeConsistentTest() throws Exception {
        int[] arrayPassed   = ARRAY_TO_SORT;
        int[] arrayReturned = this.getSorterWrapper().sort(arrayPassed);

        Assertions.assertTrue(arrayPassed.length == arrayReturned.length);
    }

    /*
     *
     *      An array passed should actually be sorted
     *
     */
    @Test
    public void sortedTest() throws Exception {
        if (this.getSorterWrapper().getSorter() instanceof DummySorter) return;

        for (int i = 0; i < ARRAYS_TO_SORT.length; i++) {
            int[] source = ARRAYS_TO_SORT[i];
            int[] sorted = this.getSorterWrapper().sort(source);

            Assertions.assertTrue(Util.isSorted(sorted));
        }

    }
}
