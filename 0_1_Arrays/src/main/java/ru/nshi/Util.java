package ru.nshi;

public class Util {
    /**
     *  Message to supply when throwing a NullPointerException
     */
    public final static String NPE_MSG = "Array is null";

    /**
     * @returns Returns true if an array is sorted. False otherwise
     *
     */
    public static boolean isSorted(int[] array) {
        // The array is sorted if n + 1th element is geq than nth element
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i + 1] < array[i]) {
                return false;
            }
        }

        return true;
    }
}
