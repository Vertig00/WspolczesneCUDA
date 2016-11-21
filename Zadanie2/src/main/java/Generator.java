import java.util.Random;

/**
 * Created by Vertig0 on 21.11.2016.
 */
public class Generator {

    private static final int range = 100;       //zakres generowanych liczb

    public static int[][] fillMatrix(int number){
        int pow = (int) Math.pow(2, number);
        int[][] matrix = new int[pow][pow];
        Random random = new Random();

        for (int i = 0 ; i < pow; i++){
            for (int j = 0; j < pow; j++){
                matrix[i][j] = random.nextInt(range);
            }
        }

        return matrix;
    }

    public static int[] fillVector(int number){
        int pow = (int) Math.pow(2, number);
        int[] vector = new int[pow];
        Random random = new Random();

        for (int i = 0; i < pow; i++){
            vector[i] = random.nextInt(range);
        }

        return vector;
    }

}
