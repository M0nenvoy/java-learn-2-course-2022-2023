package ru.nshi.task1;

import ru.nshi.sorterWrapper.SorterWrapper;
import ru.nshi.sorters.BubbleSorter;

public class BubbleSorterTest extends SorterAbstractTest {
    @Override
    protected SorterWrapper getSorterWrapper() {
        return new SorterWrapper(new BubbleSorter());
    }
}
