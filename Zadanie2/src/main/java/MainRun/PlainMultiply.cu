extern "C"
__global__ void multiply(int n, float *a, float *b, float *sum)
{
    int i = threadIdx.x ;
    int j = threadIdx.x % n;

    if (j<n)
    {
        sum[i] = a[i] * b[j];
    }

}