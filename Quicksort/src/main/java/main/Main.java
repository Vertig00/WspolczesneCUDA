package main;


import java.util.List;


/**
 * Created by lukasz on 27.09.16.
 */
public class Main {

    static double tab[] = new double[]{
            10, 32, 2.523, -1.123, 0, 12.345, -9.876};

    public static void main(String[] args) {
        mainK();
    }

    public static void mainN() {
        soutTab(tab, "Before quicksort");

        long startTime = System.nanoTime();
        QuickSort.quicksSortCalc(tab, 0, tab.length - 1);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println("Time: " + duration + "\n");

        soutTab(tab, "After quicksort");
    }

    private static void sortBundle(List<Double> list) {
        System.out.println("\nWYNIKI");

        double[] tab = new double[list.size()];
        for (int i = 0; i < tab.length; i++) {
            tab[i] = list.get(i);
        }

        long startTime, endTime, duration;
        double mili;

//        System.out.println("tab" + "\t" + "elem" + "\t" + "czas (ms)" );
        System.out.printf("%7s %-12s %-12s%n", "tablica", "elementow", "czas");

        // random
        startTime = System.nanoTime();
        tab = QuickSort.quicksSortCalc(tab, 0, tab.length - 1);
        endTime = System.nanoTime();

        duration = (endTime - startTime);
        mili = duration / 1000000.0;
//        System.out.println("---> Time RAND (mili): " + mili + "");
//        System.out.println("RAND" + "\t" + tab.length + "\t" + mili );
        System.out.printf("%7s %-12d %-12.3f%n", "RAND", tab.length, mili);

        // sorted
        startTime = System.nanoTime();
        tab = QuickSort.quicksSortCalc(tab, 0, tab.length - 1);
        endTime = System.nanoTime();

        duration = (endTime - startTime);
        mili = duration / 1000000.0;
//        System.out.println("---> Time SORT (mili): " + mili);
//        System.out.println("BEST" + "\t" + tab.length + "\t" + mili );
        System.out.printf("%7s %-12d %-12.3f%n", "SORT", tab.length, mili);

        // reverse sorted
//        tab = reverseTab(tab);
//        startTime = System.nanoTime();
//        tab = QuickSort.quicksSortCalc(tab, 0, tab.length - 1);
//        endTime = System.nanoTime();
//
//        duration = (endTime - startTime);
//        mili = duration / 1000000.0;
//        System.out.println("---> Time SORT (mili): " + mili + "\n\n\n");

        System.out.println("\n");
    }

    private static double[] reverseTab(double[] tab) {
        for (int i = 0; i < tab.length / 2; i++) {
            double temp = tab[i];
            tab[i] = tab[tab.length - i - 1];
            tab[tab.length - i - 1] = temp;
        }
        return tab;
    }

    public static void mainK() {
        Generator generator = new Generator();
        System.out.println("Wybierz metode wybierania liczb:");
        System.out.println("1 - automatyczna");
        System.out.println("2 - ręczna");
        menu((int) Generator.insertNumber(), generator);
//        generator.showNumbers();

//        TODO: Sortowanie liczb itd
        sortBundle(generator.getNumbers());

        System.out.print("Czy chcesz uruchomić program ponownie?(Tak-1/Nie-2): ");
        runProgramAgain((int)Generator.insertNumber());
    }

    private static void menu(int choice, Generator generator){
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
                System.out.println("Wprowadzono błędne dane, wprowadź jeszcze raz ");
                menu((int) Generator.insertNumber(), generator);
        }
    }

    private static void runProgramAgain(int choice){
        switch (choice){
            case 1:
                mainK();
                break;
            case 2:
                System.exit(0);
                break;
            default:
                System.out.println("Podano nieprawidłowe dane, wprowadź jeszczę raz.");
                runProgramAgain((int)Generator.insertNumber());
        }
    }

    private static void soutTab(double tab[], String text) {
        System.out.println(text);
        for (double e : tab)
            System.out.println(e);
        System.out.println();
    }



}
