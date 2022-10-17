package ru.nshi;

import java.util.HashMap;

import ru.nshi.jsonSorter.JsonSorter;
import ru.nshi.sorterWrapper.SorterWrapper;
import ru.nshi.sorters.BubbleSorter;
import ru.nshi.sorters.SelectionSorter;
import ru.nshi.sorters.MergeSorter;

/**
 * Task1
 */
public class Task1 {
    public static void main(String[] args) throws Exception {

        HashMap<String, SorterWrapper> sorters = new HashMap<>();
        sorters.put("bubble",       new SorterWrapper(new BubbleSorter()));
        sorters.put("selection",    new SorterWrapper(new SelectionSorter()));
        sorters.put("merge",        new SorterWrapper(new MergeSorter()));

        JsonSorter jsorter = new JsonSorter(sorters);

        String result = jsorter.processJson("{ \"algorithm\": \"bubble\", \"values\": [ 7, 2, 3, 4, 1 ] }");
        System.out.println(result);
    }
}
