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
        Generator generator = new Generator();
        System.out.println("Wybierz metode wybierania liczb:");
        System.out.println("1 - automatyczna");
        System.out.println("2 - ręczna");
        int choice = (int) Generator.insertNumber();
        switch (choice){
            case 1:
                System.out.print("Podaj zakres generowanych liczb: ");
                generator.setRange(Generator.insertNumber());
                generator.generateNumbers();
                break;
            case 2:
                generator.insertNumbersFromConsole();
                break;
            default:
                System.out.println("Jesteś debilem bo nie stosujesz się do instrukcji");
        }
        generator.showNumbers();
    }

    private static void soutTab(double tab[], String text) {
        System.out.println(text);
        for (double e : tab)
            System.out.println(e);
        System.out.println();
    }



}
