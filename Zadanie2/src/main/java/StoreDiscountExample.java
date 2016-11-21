import jcuda.Pointer;
import jcuda.Sizeof;
import jcuda.driver.*;
import jcuda.runtime.JCuda;

import static jcuda.driver.JCudaDriver.*;


public class StoreDiscountExample {
    public static void main(String[] args) {
        int threads = 256;
        int blocks = 1024;
        final int size = 9000000;
        byte product1[] = "abc".getBytes();
        byte product2[] = "bcd".getBytes();
        byte productList[] = new byte[size * 3];
        float productPrices[] = new float[size];
        long size_array[] = {size};

        cuInit(0);
        CUcontext pctx = new CUcontext();
        CUdevice dev = new CUdevice();
        cuDeviceGet(dev, 0);
        cuCtxCreate(pctx, 0, dev);
        CUmodule module = new CUmodule();
        cuModuleLoad(module, "StoreDiscountKernel.ptx");
        CUfunction function = new CUfunction();
        cuModuleGetFunction(function, module, "kernel");


        int j = 0;
        for (int i = 0; i < size; i++) {
            j = i * 3;
            if (i % 2 == 0) {
                productList[j] = product1[0];
                productList[j + 1] = product1[1];
                productList[j + 2] = product1[2];
            } else {
                productList[j] = product2[0];
                productList[j + 1] = product2[1];
                productList[j + 2] = product2[2];
            }

            productPrices[i] = i + 1;

        }

        printSamples(size, productList, productPrices);

        CUdeviceptr size_dev = new CUdeviceptr();
        cuMemAlloc(size_dev, Sizeof.LONG);
        cuMemcpyHtoD(size_dev, Pointer.to(size_array), Sizeof.LONG);

        CUdeviceptr productList_dev = new CUdeviceptr();
        cuMemAlloc(productList_dev, Sizeof.BYTE * 3 * size);
        cuMemcpyHtoD(productList_dev, Pointer.to(productList), Sizeof.BYTE * 3 * size);

        CUdeviceptr productPrice_dev = new CUdeviceptr();
        cuMemAlloc(productPrice_dev, Sizeof.FLOAT * size);
        cuMemcpyHtoD(productPrice_dev, Pointer.to(productPrices), Sizeof.FLOAT * size);

        Pointer kernelParameters = Pointer.to(
                Pointer.to(size_dev),
                Pointer.to(productList_dev),
                Pointer.to(productPrice_dev)
        );

        cuLaunchKernel(function,
                blocks, 1, 1,
                threads, 1, 1,
                0, null,
                kernelParameters, null);

        cuMemcpyDtoH(Pointer.to(productPrices), productPrice_dev, Sizeof.FLOAT * size);

        printSamples(size, productList, productPrices);

        JCuda.cudaFree(productList_dev);
        JCuda.cudaFree(productPrice_dev);
        JCuda.cudaFree(size_dev);
    }


    public static void printSamples(int size, byte[] productList, float[] productPrices) {
        System.out.print(String.copyValueOf(new String(productList).toCharArray(), 0, 3));
        System.out.println(" " + productPrices[0]);
        System.out.print(String.copyValueOf(new String(productList).toCharArray(), 3, 3));
        System.out.println(" " + productPrices[1]);
        System.out.print(String.copyValueOf(new String(productList).toCharArray(), 6, 3));
        System.out.println(" " + productPrices[2]);
        System.out.print(String.copyValueOf(new String(productList).toCharArray(), 9, 3));
        System.out.println(" " + productPrices[3]);
        System.out.print(String.copyValueOf(new String(productList).toCharArray(), (size - 2) * 3, 3));
        System.out.println(" " + productPrices[size - 2]);
        System.out.print(String.copyValueOf(new String(productList).toCharArray(), (size - 1) * 3, 3));
        System.out.println(" " + productPrices[size - 1]);
    }
}