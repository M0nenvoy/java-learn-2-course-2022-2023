package ru.nshi.task1;

import ru.nshi.sorterWrapper.SorterWrapper;
import ru.nshi.sorters.SelectionSorter;

public class SelectionSorterTest extends SorterAbstractTest {
    @Override
    protected SorterWrapper getSorterWrapper() {
        return new SorterWrapper(new SelectionSorter());
    }
}
