import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nex0Zero on 2016-11-21.
 */
public class RunZad2 {

    public static void main(String[] args) {
        System.out.println("SPEEDTEST :: START\n");

        List<int[][]> matrixDataSets = new ArrayList<int[][]>();
        List<int[]> vectorDataSets = new ArrayList<int[]>();

        // TODO: Wypełnienie datasetów

        for (int i = 0; i < matrixDataSets.size(); i++) {
            System.out.println("   test :: " + i);

            int[][] matrix = matrixDataSets.get(i);
            int[] vector = vectorDataSets.get(i);

            System.out.println("    n : " + vector.length);


            // JAVA segment
            long startTimeJava = System.nanoTime();
            javaTest(matrix, vector);
            long endTimeJava = System.nanoTime();

            // JAVA time measurment
            long estimatedTimeJava = endTimeJava - startTimeJava;
            double estimatedTimeJavaMilli = (double) estimatedTimeJava / 1000000.0;
            System.out.println("       JAVA time : " + estimatedTimeJavaMilli + " ms");


            // JCUDA segment
            long startTimeJcuda = System.nanoTime();
            // TODO: odpalanie dla JCUDA
            long endTimeJcuda = System.nanoTime();

            // JAVA time measurment
            long estimatedTimeJcuda = endTimeJcuda - startTimeJcuda;
            double estimatedTimeJcudaMilli = (double) estimatedTimeJcuda / 1000000.0;
            System.out.println("      JCUDA time : " + estimatedTimeJcudaMilli + " ms");


            System.out.println("");
        }

        System.out.println("SPEEDTEST :: END\n");
    }

    public static int[] javaTest(int[][] matrix, int[] vector) {
        int n = vector.length;
        int[] result = new int[n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                result[i] += vector[i] * matrix[j][i];
            }

        return result;
    }

    private static int[] jcudaTest(int[][] matrix, int[] vector) {
        int n = vector.length;
        int[] result = new int[n];


        return result;
    }

}












