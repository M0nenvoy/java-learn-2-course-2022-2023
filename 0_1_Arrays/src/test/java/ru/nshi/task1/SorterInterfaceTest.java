package ru.nshi.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ru.nshi.sorterWrapper.ISorterWrapper;
import ru.nshi.sorterWrapper.SorterWrapper;
import ru.nshi.sorters.BubbleSorter;

public class SorterInterfaceTest {

    // An array to test our sorting capabilities
    public final int[]      ARRAY_TO_SORT   = { 4, 2, 3, 5, 6, 12, 0, -1, 9 };

    /*
     *  Each test should be invoked for each implementations of the Sorter interface. Makes sense to use ParameterizedTest for it.
     *  Though, we can't pass objects as parameters to a ParameterizedTest, so we'll go about it this way.
     *  We will create an array sorter objects and will be passing an array of indexes in this array to each ParameterizedTest to address them.
     *
     *  Actually this approach isn't the best. I don't think that we can figure which sorter has failed.
     *  Maybe it would be better to write tests for each implementation in some other class. Though, it'd be a lot of typing
     */
    public ISorterWrapper isw;

    /**
     *      Create the ISorterWrapper to use in tests. By default, it uses the BubbleSorter sorting implementation (which just throws upon trying to sort).
     *      Though, it matters not, since things that we test in this test file are independent of the sorting implementation
     *      should be guaranteed by the SorterWrapper.
     */
    @BeforeEach
    public void setupISW() {
        this.isw = new SorterWrapper(new BubbleSorter());
    }

    /*
     *  Sorters should throws NullPointerException with the message "Array is null" after getting null array as a parameter.
     */
    public void nullThrowTest() {
        // Check, if we are throwing a NullPointerException at all
        NullPointerException e = Assertions.assertThrows(NullPointerException.class, () -> {
            this.isw.sort(null);
        });

        // An exception is thrown. Now we need to check if the exception has the message we need.
        String expected = "Array is null";
        String actual   = e.getMessage();
        Assertions.assertEquals(expected, actual);
    }

    /*
     *  A sorter should create a new array.
     *  In other words, we need to make sure that the arrayPassed and the arrayReturned are
     *  not the same.
     */
    @ParameterizedTest
    @ValueSource(ints = { 0, 1 })
    public void newArrayTest() throws Exception {
        int[] arrayPassed   = ARRAY_TO_SORT;
        int[] arrayReturned = this.isw.sort(arrayPassed);

        Assertions.assertFalse(arrayPassed == arrayReturned);
    }


}
