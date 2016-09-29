package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Vertig0 on 29.09.2016.
 */
class Generator {

    private List<Double> numbers = new ArrayList<Double>();
    private double n;

    Generator(double n) {
        this.n = n;
        generateNumbers();
    }

    public void generateNumbers(){
        List<Double> numbers = new ArrayList<Double>();
        Random random = new Random();
        for(int i = 0; i < n; i++){
           numbers.add(random.nextDouble()*this.n);
        }
        this.numbers = numbers;
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
}
