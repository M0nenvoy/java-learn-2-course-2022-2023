package ru.nshi.task1;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import ru.nshi.Util;
import ru.nshi.sorterWrapper.SorterWrapper;

/**
 *      Lmao, let's use inheritance to write tests. We will create an abstract SorterTest (well, this one)
 *      write all the tests we need here. And then we will be creating the children for this abstract test
 *      specifing the sorter algorithm that we need.
 *
 *      Edit: it actually worked
 */

public abstract class SorterAbstractTest {
    abstract protected SorterWrapper getSorterWrapper();

    // The array of arrays of integers to test our sorting capabilities on.
    private static Stream<int[]> arraysToSortArgFactory() {
        return Stream.of(
            new int[] {1, 2, 3, 4},
            new int[] {1, 4, 3, 2},
            new int[] {5, 4, 3, 2, 12, 8, 90, -10, 0, -10, 0, 0, 3}
        );
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
    @ParameterizedTest
    @MethodSource("arraysToSortArgFactory")
    public void arrayNotModified(int[] array) throws Exception {
        // Array in it's initial state
        int[] initial = array;

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
    @ParameterizedTest
    @MethodSource("arraysToSortArgFactory")
    public void sizeConsistentTest(int[] array) throws Exception {
        int[] arrayPassed   = array;
        int[] arrayReturned = this.getSorterWrapper().sort(arrayPassed);

        Assertions.assertTrue(arrayPassed.length == arrayReturned.length);
    }

    /*
     *
     *      An array passed should actually be sorted
     *
     */
    @ParameterizedTest
    @MethodSource("arraysToSortArgFactory")
    public void sortedTest(int[] array) throws Exception {
        int[] source = array;
        int[] sorted = this.getSorterWrapper().sort(source);

        Assertions.assertTrue(Util.isSorted(sorted));

    }
}
