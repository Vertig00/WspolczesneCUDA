import java.util.Random;

/**
 * Created by Vertig0 on 08.01.2017.
 */
public class DataProvider {


    public static float[] initializeData(int iterations, int period){
        float[] data = new float[iterations];

        Random random = new Random();
        for (int i = 0; i < iterations; i++) {
            data[i] = random.nextFloat() * period;
        }
        return data;
    }
}
