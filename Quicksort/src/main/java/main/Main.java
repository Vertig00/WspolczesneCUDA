package main;

import java.util.Scanner;

/**
 * Created by lukasz on 27.09.16.
 */
public class Main {

    static double tab[] = new double[]{
            10, 32, 2.523, -1.123, 0, 12.345, -9.876};

    public static void main(String[] args) {

    }
    public static void mainN() {
        soutTab(tab, "Before quicksort");

        QuickSort.quicksSortCalc(tab, 0, tab.length - 1);

        soutTab(tab, "After quicksort");
    }
    public static void mainK() {
        System.out.println("podaj liczbe");
        Generator generator = new Generator(insertNumber());
    }

    public static double insertNumber(){
        double number = 0;
        Scanner odczyt = new Scanner(System.in);

        number = odczyt.nextInt();
        return number;

    }

    private static void soutTab(double tab[], String text) {
        System.out.println(text);
        for (double e : tab)
            System.out.println(e);
        System.out.println();
    }



}
