package ru.nshi.jsonSorter;

/*
 *  Инструкции по сортировки. Содержит массив для сортировки и строку, описывающую
 *  необходимый метод сортировки.
 *
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
 */

public class SorterInstructions {
    public String algorithm;
    public int[] values;
}
