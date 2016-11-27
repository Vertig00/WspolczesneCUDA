package MainRun;

import jcuda.Pointer;
import jcuda.Sizeof;
import jcuda.driver.*;
import jcuda.runtime.JCuda;

import static jcuda.driver.JCudaDriver.*;

/**
 * Created by Nex0Zero on 2016-11-27.
 */
public class CzaryIMagia {

    // DATA
    static String CUClass = "PlainMultiply.cu";
    static String CUMethod = "multiply";
    static CUfunction function;
    static int threads = 8;
    static int blocks = 16;

    // TEMP - DATA
    static int size_data[] = {5};
    static float init_data[] = {1.0f, 2.0f, 3.0f, 4.0f, 6.0f};
    static float func_data[] = {0.5f, 0.5f, 0.5f, 0.5f, 0.5f};
    static float res_data[] = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f};

    public static void zaklinanie() {

        wtepneZaklinanieKarty();

        CUdeviceptr size_dev = przygotowanieOfiaryInt(size_data);
        CUdeviceptr init_dev = przygotowanieOfiaryFloat(init_data);
        CUdeviceptr func_dev = przygotowanieOfiaryFloat(func_data);
        CUdeviceptr res_dev = przygotowanieOfiaryFloat(res_data);

        // Zebranie wszystkich ofiar dla karty
        Pointer kernelParameters = Pointer.to(
                Pointer.to(size_dev),
                Pointer.to(init_dev),
                Pointer.to(func_dev),
                Pointer.to(res_dev)
        );

        // Rozpoczecie Rzucania Czarow Na Ofiary
        cuLaunchKernel(function,
                blocks, 1, 1,
                threads, 1, 1,
                0, null,
                kernelParameters, null);

        // Odczytanie woli Karty
        cuMemcpyDtoH(Pointer.to(res_data), res_dev, Sizeof.FLOAT * res_data.length);

        // Oczyszczenie stołu zaklęć
        JCuda.cudaFree(size_dev);
        JCuda.cudaFree(init_dev);
        JCuda.cudaFree(func_dev);
        JCuda.cudaFree(res_dev);
    }


    private static void wtepneZaklinanieKarty() {

        // Wstepne Zaklinanie karty graficznej
        cuInit(0);
        CUcontext pctx = new CUcontext();
        CUdevice dev = new CUdevice();
        cuDeviceGet(dev, 0);
        cuCtxCreate(pctx, 0, dev);

        // Odczytanie zwojów szkoły magii C++
        CUmodule module = new CUmodule();
        cuModuleLoad(module, CUClass);

        // Wybranie zaklęcia ze zwojów C++
//        CUfunction
        function = new CUfunction();
        cuModuleGetFunction(function, module, CUMethod);

    }

    private static CUdeviceptr przygotowanieOfiaryFloat(float[] data) {

        // Wskaźnik na ofiare
        CUdeviceptr pointer = new CUdeviceptr();
        // Zadeklarowanie wielkości ofiary
        cuMemAlloc(pointer, Sizeof.FLOAT * data.length);
        // Wysłanie ofiary do Potężnej Karty Graficznej
        cuMemcpyHtoD(pointer, Pointer.to(data), Sizeof.FLOAT * data.length);

        // Zwrot wskaznika właścicielowi ofiary
        return pointer;

    }

    private static CUdeviceptr przygotowanieOfiaryInt(int[] data) {

        // Wskaźnik na ofiare
        CUdeviceptr pointer = new CUdeviceptr();
        // Zadeklarowanie wielkości ofiary
        cuMemAlloc(pointer, Sizeof.INT * data.length);
        // Wysłanie ofiary do Potężnej Karty Graficznej
        cuMemcpyHtoD(pointer, Pointer.to(data), Sizeof.INT * data.length);

        // Zwrot wskaznika właścicielowi ofiary
        return pointer;

    }

}
