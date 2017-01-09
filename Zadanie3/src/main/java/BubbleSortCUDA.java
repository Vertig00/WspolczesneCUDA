import jcuda.Pointer;
import jcuda.Sizeof;
import jcuda.driver.*;
import jcuda.runtime.JCuda;

import java.io.IOException;

import static jcuda.driver.JCudaDriver.*;

/**
 * Created by Nex0Zero on 2017-01-09.
 */
public class BubbleSortCUDA {


    static String CUClass = "C:\\WORKSPACE\\IdeaProjects\\WspolczesneCUDA\\Zadanie3\\src\\main\\java\\BubbleOneLoop.cu";
    static String CUMethod = "bubble";

    public static float[] sortowanieBabalekowe(float[] tab_data) {
        int N = tab_data.length;
        float result_data[] = new float[N];
        int threads = 256;
        int blocks = N / threads + 1;

        CUfunction function = wtepneZaklinanieKarty(CUClass, CUMethod);

        int size_data[] = {N};
        int even_data[] = {0};
        int odd_data[] = {1};
        CUdeviceptr tab_dev = przygotowanieOfiaryFloat(tab_data);

        for (int i = 0; i < N + 1; i++) {
            int parity_data[];
            if (i % 2 == 0)
                parity_data = even_data;
            else
                parity_data = odd_data;

            Pointer kernelParameters = Pointer.to(
                    Pointer.to(size_data),
                    Pointer.to(parity_data),
                    Pointer.to(tab_dev)
            );

            cuLaunchKernel(function,
                    blocks, 1, 1,
                    threads, 1, 1,
                    0, null,
                    kernelParameters, null);

            cuMemcpyDtoH(Pointer.to(tab_data), tab_dev, Sizeof.FLOAT * result_data.length);
        }

        cuMemcpyDtoH(Pointer.to(result_data), tab_dev, Sizeof.FLOAT * result_data.length);

        JCuda.cudaFree(tab_dev);

        return result_data;
    }

//    public static float[] zaklinanieMnozenia(float[] matrix_data, float[] vector_data, int n) {
//        float result_data[] = new float[n * n];
//        int threads = obliczenieIlościApostatów(n * n);
//        int blocks = threads * 2;
//
//        // Wstępne zaklinanieMnozenia Potężnej Karty
//        CUfunction function = wtepneZaklinanieKarty(CUClass, CUMethod);
//
//        // Przygotowanie poszczególnych ofiar
//        int size_data[] = {n};
//        CUdeviceptr matrix_dev = przygotowanieOfiaryFloat(matrix_data);
//        CUdeviceptr vector_dev = przygotowanieOfiaryFloat(vector_data);
//        CUdeviceptr result_dev = przygotowanieOfiaryFloat(result_data);
//
//        // Zebranie wszystkich ofiar dla karty
//        Pointer kernelParameters = Pointer.to(
//                Pointer.to(size_data),
//                Pointer.to(matrix_dev),
//                Pointer.to(vector_dev),
//                Pointer.to(result_dev)
//        );
//
//        // Rozpoczecie Rzucania Czarow Na Ofiary
//        cuLaunchKernel(function,
//                blocks, 1, 1,
//                threads, 1, 1,
//                0, null,
//                kernelParameters, null);
//
//        // Odczytanie woli Karty
//        cuMemcpyDtoH(Pointer.to(result_data), result_dev, Sizeof.FLOAT * result_data.length);
//
//        // Oczyszczenie stołu zaklęć
//        JCuda.cudaFree(matrix_dev);
//        JCuda.cudaFree(vector_dev);
//        JCuda.cudaFree(result_dev);
//
//        return result_data;
//    }


    public static CUfunction wtepneZaklinanieKarty(String CUClass, String CUMethod) {

        // Przygotowanie supremacji PTX
        String ptxFileName = "";
        try {
            ptxFileName = Niewlasne.preparePtxFile(CUClass);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Wstepne Zaklinanie karty graficznej
        cuInit(0);
        CUcontext pctx = new CUcontext();
        CUdevice dev = new CUdevice();
        cuDeviceGet(dev, 0);
        cuCtxCreate(pctx, 0, dev);

        // Odczytanie zwojów szkoły magii C++
        CUmodule module = new CUmodule();
        cuModuleLoad(module, ptxFileName);

        // Wybranie zaklęcia ze zwojów C++
        CUfunction function = new CUfunction();
        cuModuleGetFunction(function, module, CUMethod);

        return function;

    }

    public static CUdeviceptr przygotowanieOfiaryFloat(float[] data) {

        // Wskaźnik na ofiare
        CUdeviceptr pointer = new CUdeviceptr();
        // Zadeklarowanie wielkości ofiary
        cuMemAlloc(pointer, Sizeof.FLOAT * data.length);
        // Wysłanie ofiary do Potężnej Karty Graficznej
        cuMemcpyHtoD(pointer, Pointer.to(data), Sizeof.FLOAT * data.length);

        // Zwrot wskaznika właścicielowi ofiary
        return pointer;

    }

    public static int obliczenieIlościApostatów(int n) {
        int required = n;
        int powerOf2 = 1;

        while ((int) Math.pow(2, powerOf2) < required)
            powerOf2++;

        return (int) Math.pow(2, powerOf2);

    }


}
