import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vertig0 on 06.12.2016.
 */
public class Main {

    public static void main(String[] args) {
        List<float[]> tableDataSets = new ArrayList<float[]>();

        // TODO: Wypełnienie datasetów
        int startPow = 2;
        for (int i = startPow; i < 15; i++) {
            tableDataSets.add(DataProvider.initializeData((int) Math.pow(2, i), 4096.0f));
        }


        for (int i = 0; i < tableDataSets.size(); i++) {
            System.out.println("   test :: 2^" + (i + startPow));

            float[] table = tableDataSets.get(i);

            System.out.println("    n : " + table.length);


            // JAVA segment
            long startTimeJava = System.nanoTime();
            float[] javaResult = javaTest(table);
            long endTimeJava = System.nanoTime();

            // JAVA time measurment
            long estimatedTimeJava = endTimeJava - startTimeJava;
            double estimatedTimeJavaMilli = (double) estimatedTimeJava / 1000000.0;
            System.out.println("       JAVA time : " + estimatedTimeJavaMilli + " ms");

            // ----------------------------------------------------------------------------------

            // JCUDA segment
            long startTimeJcuda = System.nanoTime();
            float[] cudaResult = BubbleSortCUDA.sortowanieBabalekowe(table);
            long endTimeJcuda = System.nanoTime();

            // JAVA time measurment
            long estimatedTimeJcuda = endTimeJcuda - startTimeJcuda;
            double estimatedTimeJcudaMilli = (double) estimatedTimeJcuda / 1000000.0;
            System.out.println("      JCUDA time : " + estimatedTimeJcudaMilli + " ms");

            // ----------------------------------------------------------------------------------

            boolean equal = true;
            if (javaResult.length != cudaResult.length)
                equal = false;
            else
                for (int j = 0; j < javaResult.length; j++)
                    if (javaResult[j] != cudaResult[j] && equal) {
                        equal = false;
                    }
            System.out.println("      EQUAL : " + equal);

            System.out.println("");
        }


    }

    public static float[] javaTest(float[] table) {
        float[] tab = table.clone();
        float temp;
        int zmiana = 1;
        while (zmiana > 0) {
            zmiana = 0;
            for (int i = 0; i < tab.length - 1; i++) {
                if (tab[i] > tab[i + 1]) {
                    temp = tab[i + 1];
                    tab[i + 1] = tab[i];
                    tab[i] = temp;
                    zmiana++;
                }
            }
        }
        return tab;
    }

    private static void test() {
        System.out.println("Zadanie 3");

        float[] tab = DataProvider.initializeData(8, 45.5f);

        printTab(tab, "przed");

//        float[] result = BubbleSortCUDA.sortowanieBabalekowe(tab);
        float[] result = javaTest(tab);

        printTab(result, "po");
    }

    public static void printTab(float[] tab, String name) {
        System.out.println("=== " + name + " ===");
        for (float el : tab)
            System.out.print(el + " ");
        System.out.println("");
    }

}
