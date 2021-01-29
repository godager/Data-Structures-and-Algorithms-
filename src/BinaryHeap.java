public class BinaryHeap {
   int[] heap;

   public BinaryHeap (int i) {
       heap = new int [i];
   }

    //Swaps two elements in array
    private void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private void insert(int[]A, int x) {
        int n = A.length -1;

        //Insert x at end of heap
        A[n] = x;
        int i = n;

        while (i > 0 && A[i] < A[(int) Math.floor((i - 1) / 2)]) {
            swap(A, A[i], A[(int) Math.floor((i - 1) / 2)]);
        }
        heap = A;
    }
}
