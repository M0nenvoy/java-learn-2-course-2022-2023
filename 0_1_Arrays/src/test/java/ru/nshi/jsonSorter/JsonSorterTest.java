package ru.nshi.jsonSorter;

import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.nshi.Util;
import ru.nshi.sorterWrapper.SorterWrapper;
import ru.nshi.sorters.BubbleSorter;
import ru.nshi.sorters.MergeSorter;
import ru.nshi.sorters.SelectionSorter;

public class JsonSorterTest {
    JsonSorter jsorter;

    @BeforeEach
    public void setupJsonSorter () {
        HashMap<String, SorterWrapper> sorters = new HashMap<>();
        sorters.put("bubble",       new SorterWrapper(new BubbleSorter()));
        sorters.put("selection",    new SorterWrapper(new SelectionSorter()));
        sorters.put("merge",        new SorterWrapper(new MergeSorter()));

        this.jsorter = new JsonSorter(sorters);
    }

    /**
     *      If jsorter is fed with the sorting algorithm it doesn't support, it should respond
     *      with the json containing errorMessage: "Algorithm is not supported"
     */
    @Test
    public void unsupportedAlgorithmTest() {
        String json = " { \"algorithm\" : \"notThere\", \"values\": [ 1, 2, 3, 4 ] }";
        String result = jsorter.processJson(json);

        Assertions.assertTrue(result.contains("errorMessage"));
        Assertions.assertTrue(result.contains("Algorithm is not supported"));
    }

    /**
     *      If jsorter is fed with the null array it should return the
     *      json containing: errorMessage: "Array is null"
     */
    @Test
    public void nullArrayTest() {
        String json = " { \"algorithm\" : \"bubble\", \"values\": null }";
        String result = jsorter.processJson(json);

        Assertions.assertTrue(result.contains("errorMessage"));
        Assertions.assertTrue(result.contains("Array is null"));
    }

    /**
     *      Test if the array supplied gets sorted
     */
    @Test
    public void sortArrayTest() throws Exception {
        String json = " { \"algorithm\" : \"merge\", \"values\": [ 4, 3, 5, 18, 90, -10, 7 ] }";
        String result = jsorter.processJson(json);

        ObjectMapper mapper = new ObjectMapper();

        SorterResult sr = mapper.readValue(result, SorterResult.class);

        Assertions.assertTrue(Util.isSorted(sr.values));
    }

    /**
     *      It would actually be nice to test if the exceution time is measured properly but well, how do you do that?
     *      It's system dependent and all.
     */
}
