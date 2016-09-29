package main;

/**
 * Created by Nex0Zero on 2016-09-29.
 */
public class QuickSort {

    public static void quicksSortCalc(double tablica[], int x, int y) {

        int i, j;
        double v, temp;

        i = x;
        j = y;
        v = tablica[(x + y) / 2];
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
            quicksSortCalc(tablica, x, j);
        if (i < y)
            quicksSortCalc(tablica, i, y);
    }

}
