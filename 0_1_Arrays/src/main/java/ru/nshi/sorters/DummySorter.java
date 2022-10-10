package ru.nshi.sorters;

/**
 *      The dummy representation of the Sorter interface. Doesn't actually sort anything.
 *      The purpose is to use it in the SorterInterfaceTest.
 *
 *      Though, now we have a Sorter implementation that doesn't actually sort anything.
 *      Which is, like, wrong. Honestly, I don't even know how to go about it.
 *
 */

public class DummySorter implements Sorter {
    @Override
    public void sort(int[] source) throws Exception {
    }
}
