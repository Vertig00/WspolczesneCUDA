package MainRun;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nex0Zero on 2016-11-21.
 */
public class RunZad2 {

    public static void main(String[] args) {
        System.out.println("SPEEDTEST :: START\n");

//        int size_data[] = {3};
//        float init_data[] = {1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 3.0f, 3.0f, 3.0f};
//        float func_data[] = {1.0f, 2.0f, 3.0f};


        List<float[][]> matrixDataSets = new ArrayList<float[][]>();
        List<float[]> vectorDataSets = new ArrayList<float[]>();

        // TODO: Wypełnienie datasetów
        for (int i = 4; i < 15; i++) {
            matrixDataSets.add(Generator.fillMatrix(i));
            vectorDataSets.add(Generator.fillVector(i));
        }

        for (int i = 0; i < matrixDataSets.size(); i++) {
            System.out.println("   test :: 2^" + (i + 4));

            float[][] matrix = matrixDataSets.get(i);
            float[] vector = vectorDataSets.get(i);

            System.out.println("    n : " + vector.length);


            // JAVA segment
            long startTimeJava = System.nanoTime();
            javaTest(matrix, vector);
            long endTimeJava = System.nanoTime();

            // JAVA time measurment
            long estimatedTimeJava = endTimeJava - startTimeJava;
            double estimatedTimeJavaMilli = (double) estimatedTimeJava / 1000000.0;
            System.out.println("       JAVA time : " + estimatedTimeJavaMilli + " ms");

            // ----------------------------------------------------------------------------------

            float[] matrix1d = matrix2dTo1d(matrix);
            int size_data[] = {vector.length};

            // JCUDA segment
            long startTimeJcuda = System.nanoTime();
            float result_data1[] = CzaryIMagia.zaklinanieMnozenia(matrix1d, vector, size_data[0]);
            float result_data2[] = CzaryIMagia.zaklinanieDodawania(result_data1, size_data[0]);
            long endTimeJcuda = System.nanoTime();

            // JAVA time measurment
            long estimatedTimeJcuda = endTimeJcuda - startTimeJcuda;
            double estimatedTimeJcudaMilli = (double) estimatedTimeJcuda / 1000000.0;
            System.out.println("      JCUDA time : " + estimatedTimeJcudaMilli + " ms");


            System.out.println("");
        }

        System.out.println("SPEEDTEST :: END\n");
    }

    public static float[] javaTest(float[][] matrix, float[] vector) {
        int n = vector.length;
        float[] result = new float[n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                result[i] += vector[i] * matrix[j][i];
            }

        return result;
    }

//    private static int[] jcudaTest(int[][] matrix, int[] vector) {
//        int n = vector.length;
//        int[] result = new int[n];
//
//        int[] matrix1d = matrix2dTo1d(matrix);
//
//        // Initialize JCublas
//        JCublas.cublasInit();
//
//        // Allocate memory on the device
//        Pointer d_Matrix = new Pointer();
//        Pointer d_Vector = new Pointer();
//        Pointer d_Result = new Pointer();
//        JCublas.cublasAlloc(n * n, Sizeof.INT, d_Matrix);
//        JCublas.cublasAlloc(n, Sizeof.INT, d_Vector);
//        JCublas.cublasAlloc(n, Sizeof.INT, d_Result);
//
//        // Copy the memory from the host to the device
//        JCublas.cublasSetVector(n * n, Sizeof.INT, Pointer.to(matrix1d), 1, d_Matrix, 1);
//        JCublas.cublasSetVector(n, Sizeof.INT, Pointer.to(vector), 1, d_Vector, 1);
//        JCublas.cublasSetVector(n, Sizeof.INT, Pointer.to(result), 1, d_Result, 1);
//
//
//        // Execute Ctbmv
////        JCublas.cublasCtbmv();
//
//        // Copy the result from the device to the host
////        JCublas.cublasGetVector(nn, Sizeof.FLOAT, d_C, 1, Pointer.to(C), 1);
//
//        // Clean up
//        JCublas.cublasFree(d_Matrix);
//        JCublas.cublasFree(d_Vector);
//        JCublas.cublasFree(d_Result);
//
//        JCublas.cublasShutdown();
//
//        return result;
//    }

    private static float[] matrix2dTo1d(float[][] matrix2d) {
        int n = matrix2d.length;
        float[] matrix1d = new float[n * n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                matrix1d[i * n + j] = matrix2d[i][j];

        return matrix1d;
    }

}












