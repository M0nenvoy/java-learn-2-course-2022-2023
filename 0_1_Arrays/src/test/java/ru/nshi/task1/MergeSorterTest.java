package ru.nshi.task1;

import ru.nshi.sorterWrapper.SorterWrapper;
import ru.nshi.sorters.MergeSorter;

/**
 * MergeSorterTest
 */
public class MergeSorterTest extends SorterAbstractTest {
    @Override
    protected SorterWrapper getSorterWrapper() {
        return new SorterWrapper(new MergeSorter());
    }
}
