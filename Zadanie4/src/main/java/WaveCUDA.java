import jcuda.Pointer;
import jcuda.Sizeof;
import jcuda.driver.*;
import jcuda.runtime.JCuda;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static jcuda.driver.JCudaDriver.*;

/**
 * Created by Nex0Zero on 2017-01-16.
 */
public class WaveCUDA {

    static String CUClass = "C:\\WORKSPACE\\IdeaProjects\\WspolczesneCUDA\\Zadanie4\\src\\main\\java\\Wave.cu";
    static String CUMethod = "wavee";

    public static int[] obliczenieFali(BufferedImage image, double t) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[] tablica = new int[width * height];
        int rowSize = width;
        int centerX = image.getWidth() / 2;
        int centerY = image.getHeight() / 2;
        float A = 1.0f;
        float lambda = 50;
        float time = (float) t;
        float fi = -1;

        int threads = 256;
        int blocks = tablica.length / threads + 1;

        /** FUNCTION */
        CUfunction function = wtepneZaklinanieKarty(CUClass, CUMethod);

        /** DATA */
        CUdeviceptr tab_dev = przygotowanieOfiaryInt(tablica);
        int rowSize_data[] = {rowSize};
        int centerX_data[] = {centerX};
        int centerY_data[] = {centerY};
        float A_data[] = {A};
        float lambda_data[] = {lambda};
        float time_data[] = {time};
        float fi_data[] = {fi};
        int N_data[] = {tablica.length};

        /** KERNEL PARAMETERS */
        Pointer kernelParameters = Pointer.to(
                Pointer.to(tab_dev),
                Pointer.to(rowSize_data),
                Pointer.to(centerX_data),
                Pointer.to(centerY_data),
                Pointer.to(A_data),
                Pointer.to(lambda_data),
                Pointer.to(time_data),
                Pointer.to(fi_data),
                Pointer.to(N_data)
        );

        /** LAUNCH */
        cuLaunchKernel(function,
                blocks, 1, 1,
                threads, 1, 1,
                0, null,
                kernelParameters, null);

        /** RETURN DATA */
        int[] result = new int[width * height];
        cuMemcpyDtoH(Pointer.to(result), tab_dev, Sizeof.INT * result.length);

        JCuda.cudaFree(tab_dev);
        JCuda.cudaFreeHost(kernelParameters);

        return result;
    }


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

    public static CUdeviceptr przygotowanieOfiaryInt(int[] data) {

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
