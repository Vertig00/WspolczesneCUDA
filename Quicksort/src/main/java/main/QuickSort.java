package main;

/**
 * Created by Nex0Zero on 2016-09-29.
 */
public class QuickSort {

    /**
     * Quicksort Example 2
     */
    private static double[] numbers;
    private static int number;

    /**
     * Quicksort Example 1
     */
    public static double[] quickSortCalc(double tablica[], int x, int y) {

        int i, j;
        double v, temp;

        i = x;
        j = y;
//        v = tablica[(x + y) / 2];
        v = tablica[x];
        do {
            while (tablica[i] < v)
                i++;
            while (v < tablica[j])
                j--;
            if (i <= j) {
                temp = tablica[i];
                tablica[i] = tablica[j];
                tablica[j] = temp;
                i++;
                j--;
            }
        }
        while (i <= j);
        if (x < j)
            quickSortCalc(tablica, x, j);
        if (i < y)
            quickSortCalc(tablica, i, y);

        return tablica;
    }


}
