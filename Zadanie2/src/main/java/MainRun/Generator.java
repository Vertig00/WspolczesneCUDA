package MainRun;

import java.util.Random;

/**
 * Created by Vertig0 on 21.11.2016.
 */
public class Generator {

    private static final int range = 100;       //zakres generowanych liczb

    public static float[][] fillMatrix(int number) {
        int pow = (int) Math.pow(2, number);
        float[][] matrix = new float[pow][pow];
        Random random = new Random();

        for (int i = 0; i < pow; i++){
            for (int j = 0; j < pow; j++){
                matrix[i][j] = random.nextFloat();
            }
        }

        return matrix;
    }

    public static float[] fillVector(int number) {
        int pow = (int) Math.pow(2, number);
        float[] vector = new float[pow];
        Random random = new Random();

        for (int i = 0; i < pow; i++){
            vector[i] = random.nextFloat();
        }

        return vector;
    }

}
