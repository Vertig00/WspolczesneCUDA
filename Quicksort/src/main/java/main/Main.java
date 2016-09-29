package main;

/**
 * Created by lukasz on 27.09.16.
 */
public class Main {

    static double tab[] = new double[]{
            10, 32, 2.523, -1.123, 0, 12.345, -9.876};

    public static void main(String[] args) {

        soutTab(tab, "Before quicksort");

        QuickSort.quicksSortCalc(tab, 0, tab.length - 1);

        soutTab(tab, "After quicksort");

    }

    private static void soutTab(double tab[], String text) {
        System.out.println(text);
        for (double e : tab)
            System.out.println(e);
        System.out.println();
    }



}
