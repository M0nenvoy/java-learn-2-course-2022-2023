package ru.nshi.sorterWrapper;

import ru.nshi.Util;
import ru.nshi.sorters.Sorter;

/*
 *  SorterWrapper будет обеспечивать контракт, a поле
 *  sorter будет отвечать за конкретный метод сортировки.
 *
 */

public class SorterWrapper implements ISorterWrapper {
    private Sorter sorter;

    @Override
    public int[] sort(int[] source) throws Exception {
        // If the supplied array is null, we should throws NullPointerException with the message:
        // "Array is null"
        if (source == null) {
            throw new NullPointerException(Util.NPE_MSG);
        }

        // If the supplied array is empty, return the empty copy
        if (source.length == 0) return new int[0];

        // As we don't want to modify the contents of the supplied array, we should make a copy of it and
        // further operate on it.

        int[] copy = source.clone();

        // If the size of the passed array is 1 then, well, it's already sorted
        if (copy.length == 1) return copy;

        sorter.sort(copy);
        return copy;
    }

    public SorterWrapper(Sorter sorter) throws NullPointerException {
        // Why would you even do this
        if (sorter == null) {
            throw new NullPointerException("Null sorter is not allowed");
        }

        this.sorter = sorter;
    }

    public Sorter getSorter() {
        return sorter;
    }
}
