package main;

import java.util.Scanner;

/**
 * Created by lukasz on 27.09.16.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("podaj liczbe");
        Generator generator = new Generator(insertNumber());

    }


    public static double insertNumber(){
        double number = 0;
        Scanner odczyt = new Scanner(System.in);

        number = odczyt.nextInt();
        return number;
    }

}
