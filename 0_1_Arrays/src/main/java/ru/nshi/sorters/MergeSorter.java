package ru.nshi.sorters;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
 *
 *  Merge sort. The recursive algorithm continuously splits the array in half until it
 *  cannot be further divided. This means that if the array becomes empty or has only one element
 *  left, the dividing will stop. The idea is to take two smaller sorted arrays and combine them to make a larger one.
 *  And this large one turns out to be sorted...
 *
 *  Anyway, here the page explaining this algorithm: https://www.geeksforgeeks.org/merge-sort/
 *
 *  Time  complexity: O (n log n)
 *  Space complexity: O (n)
 */
public class MergeSorter implements Sorter {

    @Override
    public void sort(int [] source) {
        // Sorter wrapper should guarantee that source isn't null
        assert(source != null);

        // Results of the divisions are put in the mergeQueue.
        List<int[]> mergeQueue = new LinkedList<>();

        // Fill the merge queue
        iterate(source, mergeQueue);

        // Assemble the merge queue into a sorted array
        int[] result = assembleMergeQueue(mergeQueue);
        System.arraycopy(result, 0, source, 0, result.length);
    }

    private void iterate(int [] source, List<int[]> mergeQueue) {
        // Length of the source array shouldn't be 0 here... So just to be safe:
        assert(source.length != 0);

        // If we have decreased the source array to its elemental state, we can put it in the merge queue:
        if (source.length == 1) {
            mergeQueue.add(source);
            return;
        }
        // Length is more than 2. Time to divide and conquer

        int center = findDivisionPoint(source);
        // First and second parts of the division
        int[] first     = Arrays.copyOfRange(source, 0, center);
        int[] second    = Arrays.copyOfRange(source, center, source.length);

        iterate(first, mergeQueue);
        iterate(second, mergeQueue);
    }

    /**
     *      The finishing step of the algorithm. Assemble the merge queue into a sorted array.
     *      @return The sorted array
     */
    private int[] assembleMergeQueue(List<int[]> mergeQueue) {
        // Size of mergeQueue is even
        boolean even = mergeQueue.size() % 2 == 0;

        // Merge every element of the merge queue with the neighboring one and put the Results
        // in the transientMergeQueue.

        // In the cycle below we refer to two indexes at and step two indexes per iteration.
        // It can cause OutOfArrayBounds error if the size of merge queue is not even. So to work around that:
        int iterationSize = mergeQueue.size();
        if (!even) iterationSize -= 1;
        List<int[]> transientMergeQueue = new LinkedList<>();
        for (int i = 0; i < iterationSize; i += 2) {
            int[] first  = mergeQueue.get(i + 0);
            int[] second = mergeQueue.get(i + 1);
            int[] mergeResult = merge(first, second);
            transientMergeQueue.add(mergeResult);
        }

        // If the size of the mergeQueue is not even, then we aren't capturing the last element of the mergeQueue in the above cycle.
        // So, ugh, I will just pass it down to the next recursion layer. It'll take care of it.
        //
        //                                                  V Pushing the last element of the merge queue in the transientMergeQueue
        if (!even) transientMergeQueue.add(mergeQueue.get(mergeQueue.size() - 1));

        // If transientMergeQueue has only one element than we are done. This element is the sorted array we need.
        if (transientMergeQueue.size() == 1) {
            return transientMergeQueue.get(0);
        } else {
            return assembleMergeQueue(transientMergeQueue);
        }

    }


    /**
     *      Merge two arrays into a sorted one.
     *      @return The resulting sorted array.
     */
    private int[] merge(int[] first, int[] second) {
        int[] retval = new int[first.length + second.length];
        int firstPtr = 0, secondPtr = 0, retvalPtr = 0;

        // Within the while loop, there is a comparison between the elements of both the left and right lists.
        // After each comparison, the output list is populated within the two compared elements.
        // The pointer of the list of the appended element is incremented.
        while ( firstPtr < first.length && secondPtr < second.length) {
            int firstValue  = first[firstPtr];
            int secondValue = second[secondPtr];
            if (firstValue <= secondValue) {
                retval[retvalPtr] = firstValue;
                firstPtr++;
            } else {
                retval[retvalPtr] = secondValue;
                secondPtr++;
            }

            retvalPtr++;
        }

        // The remaining elements to be added to the sorted list are elements obtained from the current pointer value to the end of the respective list.

        // How many elements do we copy from the first array
        int copyFromFirst = first.length - firstPtr;
        System.arraycopy(first, firstPtr, retval, retvalPtr, copyFromFirst);

        retvalPtr += copyFromFirst;
        System.arraycopy(second, secondPtr, retval, retvalPtr, second.length - secondPtr);

        return retval;
    }

    /*
     *   Divide the array in two parts that are close to being equal.
     */
    private int findDivisionPoint(int [] source) {
        // Whether the length of the array is an even number.
        boolean even = source.length % 2 == 0;

        // The point of the division
        int center;

        if (even) { // If the size of an array is equal, divide it in two equal parts.
            center = source.length / 2;
        } else { // Else, make the first part larger than the second
            center = source.length / 2 + 1;
        }

        return center;
    }

}
