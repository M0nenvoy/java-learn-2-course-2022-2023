package ru.nshi.jsonSorter;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.nshi.sorterWrapper.SorterWrapper;

/*
 *
 *
 *  Задание 2
*c  Написать программу, читающую файл содержащий JSON.

 *  algorithm - реализация сортировки
 *  array - массив содержащий целые числа

 *  Пример содержимого файла:
 *
 *  {
 *      "algorithm": "bubble",
 *      "values": [
 *          7,
 *          2,
 *          3,
 *          4,
 *          1
 *      ]
 *  }
 *
 *  Данный массив должен быть отсортирован выбранной сортировкой. Далее должен быть
 *  произведен замер скорости выполнения в миллисекундах. В результате должен быть выведен следующий
 *  JSON:
 *
 *  {
 *      "time": 100,
 *      "values": [
 *          1, 2, 3, 4, 7
 *      ]
 *  }
 *
 *  В случае ошибки, следует вернуть следующий JSON:
 *  { "errorMessage": "Array is null" }
 *
 *  Дополнительно
 *  Реализовать добавление новых сортировщиков с минимальным количеством изменения в коде
 */


public class JsonSorter {
    /*
     *      Сделаем так, что JsonSorter будет принимать словарь с ключами-строками и значениями-сортировщиками
     *      Ключ - это строка, которая идентифицирует необходимый алгоритм сортировки в json
     */
    private HashMap<String, SorterWrapper> sorters;
    public JsonSorter(HashMap<String, SorterWrapper> sorters) {
        this.sorters = sorters;
    }

    /**
     *      Process the input json string with sorting instructions. Return the resulting json string.
     */
    public String processJson(String jsonString) throws Exception {
        // Parse data from the jsonString to the java-object (SorterInstructions)
        ObjectMapper mapper = new ObjectMapper();
        SorterInstructions si = null;

        si = mapper.readValue(jsonString, SorterInstructions.class);

        if (si.values == null) {
            return mapper.writeValueAsString(new SorterError("Array is null"));
        }

        // The algorithm used for sorting.
        String algorithm = si.algorithm;

        long timeBeforeSort = System.currentTimeMillis();
        int[] sorted;
        try {
            sorted = sort(si.values, algorithm);
        } catch (AlgorithmNotSupportedException e) {
            // Check, if we support this type of sorter.
            return mapper.writeValueAsString(
                new SorterError(String.format("Algorithm is not supported (%s)", algorithm))
            );
        } catch (Exception e) {
            return mapper.writeValueAsString(
                new SorterError(String.format("Failed to sort an array (%s)", algorithm))
            );
        }

        long timeAfterSort = System.currentTimeMillis();

        // Time to sort the array in milliseconds
        long time = timeAfterSort - timeBeforeSort;

        // Write the json string result.
        String retval = mapper.writeValueAsString(new SorterResult(sorted, time));

        return retval;
    }
    public int[] sort(int[] array, String algorithm) throws Exception {

        // Check, if we support this type of sorter.
        if (!sorters.keySet().contains(algorithm)) {
            throw new AlgorithmNotSupportedException(
                String.format("Algorithm (%s) is not supported", algorithm)
            );
        }
        int[] sorted;
        sorted = sorters.get(algorithm).sort(array);

        return sorted;
    }
}
