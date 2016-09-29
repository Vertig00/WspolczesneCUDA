package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Vertig0 on 29.09.2016.
 */
class Generator {

    private List<Double> numbers = new ArrayList<Double>();
    private double n;
    private double range;

    Generator() {
        System.out.print("Podaj ilość elementów tablicy liczb: ");
        this.n = insertNumber();
    }

    public void generateNumbers(){
        List<Double> numbers = new ArrayList<Double>();
        Random random = new Random();
        for(int i = 0; i < n; i++){
           numbers.add(random.nextDouble() * this.range);
        }
        this.numbers = numbers;
    }

    public void insertNumbersFromConsole(){
        List<Double> numbers = new ArrayList<Double>();
        for (int i = 0; i < this.n; i++){
            System.out.print("Podaj " + i+1 + " liczbę: ");
            numbers.add(insertNumber());
        }
        this.numbers = numbers;
    }

    public static double insertNumber(){
        double number = 0;
        Scanner odczyt = new Scanner(System.in);

        number = odczyt.nextInt();
        return number;
    }

    public void showNumbers(){
        for (double number:this.numbers) {
            System.out.println(number);
        }
    }

    public void insertNumbers(){
        List<Double> numbers = new ArrayList<Double>();
    }

    public List<Double> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Double> numbers) {
        this.numbers = numbers;
    }


    public double getN() {
        return n;
    }

    public void setN(double n) {
        this.n = n;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }
}
