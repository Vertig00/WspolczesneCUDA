
import java.io.IOException;

import static java.lang.Math.sin;


/**
 * Created by Nex0Zero on 2017-01-16.
 */

/**
 * y(t,z) = A * sin(wt - kz + fi)
 * y - miara odchylenia od stanu równowagi
 * A - amplituda fali
 * w - częstość fali
 * t - czas
 * k - wektor fali
 * z - współrzędna położenia
 * fi - faza początkowa t=0 z=0
 * <p>
 * w = 2*pi/T
 * T - okres drgań
 * <p>
 * k = 2*pi/lambda
 * lambda - długość fali
 * <p>
 * y(t,z) = A * sin(2pi/T * t - 2pi/lambda * z + fi)
 * <p>
 * T = lambda/v
 * v - prędkość rozchodzenia się fali
 */
public class Test {

    public static String ORIGINAL = "C:\\WORKSPACE\\IdeaProjects\\#DATA\\CUDA\\original.bmp";
    public static String WAVE = "C:\\WORKSPACE\\IdeaProjects\\#DATA\\CUDA\\wave.bmp";

    public static double VAR_A = 1.0;           // amplituda fali
    public static double VAR_lambda = 20;       // długość fali
    public static double VAR_start_t = 0;       // czas początkowy
    public static double VAR_fi = -1;            // faza początkowa t=0 z=0

    public static void main(String[] args) throws IOException {

    }


    private static void faza1d() {
        double A = 1.0;
        double pi = Math.PI;
        double lambda = 20;
        double v = 1;
        double T = lambda / v;
        double t = 0;
        double fi = 0;

        double tab[] = new double[21];

        for (int z = 0; z < tab.length; z++) {
            double w = 2 * pi / T;
            double k = 2 * pi / lambda;
            tab[z] = A * sin(w * t - k * z + fi);

            System.out.printf(" %2.5f", tab[z]);
            if (z % 10 == 9)
                System.out.println("");
        }

    }

}













