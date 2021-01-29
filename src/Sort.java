import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Sort {


    public ArrayList<Integer> unionSorted(int[]a, int[]b, int n) {
        ArrayList<Integer> union = new ArrayList<>();

        int ai = 0;
        int bi = 0;
        for (int i = 0; i < n; i++) {

            if (union.size() > i) {
                while (ai != union.get(i)) {
                    ai++;
                }
                while (bi != union.get(i)) {
                    bi++;
                }
            }

            int min = Math.min(a[i], b[i]);
            union.add(i, min);
        }
        return union;
    }

    //Bubble sort in O(n^2)
    public int[] bubbleSort(int[]a) {
        for (int i = 0; i < a.length-1; i++) {
            //After i outer loops, greatest elements are in the end
            boolean sub = false;
            for (int j = 0; j <a.length-i-1; j++) {
                int first = a[j];
                int second = a[j + 1];
                if (first > second) {
                    a[j] = second;
                    a[j + 1] = first;
                    sub = true;
                }
            }
            //If inner loop did no changes, all elements are sorted
            if (!sub) break;
        }
        return a;
    }

    //Moves smallest so far to front for each iteration in O(n^2) time,
    //but usually faster than bubble sort

    public int[] selectionSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            //k = smallest so far
            int k = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[k]) k = j;
            }
            if (i != k) {
                //Change position
                int first = a[i];
                int second = a[k];
                a[i] = second;
                a[k] = first;
            }
        }
        return a;
    }

    //How you would sort playing cards (also O(n^2))
    //But better than bubble and selection sort, because we
    //can break out of inner loop more often. Fast if quite good
    //sorted array or small array.
    public int[] insertionSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int j = i;
            while(j > 0 && a[j - 1] > a[j]) {
                //Change position
                int first = a[j-1];
                int second = a[j];
                a[j-1] = second;
                a[j] = first;
                j--;
            }
        }
        return a;
    }

    //Fast sort for larger input size n. O(n log n) time.
    public int[] heapSort(int[] a) {
        //make array a max-heap
        //Root (biggest element) is on index 0
        //left child on index 2*i + 1, right child on 2*i + 2, where i = index of parent
        buildMaxHeap(a, a.length);

        for (int i = a.length - 1; i > 0; i--) {
            //Change position for a[0] and a[i]
            int tmp = a[0];
            a[0] = a[i];
            a[i] = tmp;

            bubbleDown(a, 0, i);
        }
        return a;
    }

    //Builds a max heap in O(n) time
    public int[] buildMaxHeap (int[] a, int n) {
        for (int i = n/2; i > 0; i--) {
            bubbleDown(a, i, n);
        }
        return a;
    }

    //Help method for buildmaxHeap, i is root, n size and a the array.
    //O(log n)
    public int[] bubbleDown(int[]a, int i, int n) {
        int largest = i;
        int left = (2 * i) + 1;
        int right = (2 * i) + 2;

        if (left < n && a[largest] < a[left]) {
            //Change position
            int tmp = left;
            left = largest;
            largest = tmp;
        }
        if (right < n && a[largest] < a[right]) {
            //Change position
            int tmp = right;
            right = largest;
            largest = tmp;
        }
        if (i != largest) {
            //Change position
            int tmp = a[i];
            a[i] = a[largest];
            a[largest] = tmp;
            bubbleDown(a, largest, n);
        }
        return a;
    }

    //Splits arrays in two parts recursively and merge them in order. O(n log n)
    public int[] mergeSort(int[] a) {
        //Base case:
        if (a.length <= 1) return a;
        int i = a.length/2;
        int [] a1 = mergeSort(Arrays.copyOfRange(a,0,i));
        int [] a2 = mergeSort(Arrays.copyOfRange(a,i,a.length));
        return merge(a1, a2, a);
    }

    //Merges two arrays to one sorted. O(n)
    public int[] merge(int[] a1, int []a2, int[]a) {
        int i = 0;
        int j = 0;

        while (i < a1.length && j < a2.length) {
            //Put smallest element between arrays in new array a
            if (a1[i] < a2[j]) {
                a[i + j] = a1[i];
                i++;
            }
            else {
                a[i + j] = a2[j];
                j++;
            }
        }

        //If arrays of different sizes:
        while (i < a1.length) {
            a[i+j] = a1[i];
            i++;
        }
        while (j < a2.length) {
            a[i+j] = a2[j];
            j++;
        }
        return a;
    }

    //Recursive partition to sort in O(n log N) time
    //Usually faster than merge sort (you have to be very unlucky
    //with random pivots to get something like O^2 time)
    public int[] quickSort(int[] a, int low, int high) {
        if (low >= high) {
            return a;
        }
        int p = partition(a, low, high);
        quickSort(a, low, p-1);
        quickSort(a, p + 1, high);
        return a;
    }

    //Chooses random index as pivot and pushes smaller element to left
    //and bigger element to to right of this. O(high-low)
    public int partition (int[] a, int low, int high) {
        Random ran = new Random();
        int p = ran.nextInt(high-1) + low;

        //Change position
        int tmp = a[p];
        a[p] = a[high];
        a[high] = tmp;

        int pivot = a[high];
        int left = low;
        int right = high -1;

        while (left <= right) {
            while(left <= right && a[left] < pivot) {
                left++;
            }
            while(right >= left && a[right] >= pivot) {
                right = right - 1;
            }
            if (left < right) {
                //Change position
                int tmp2 = a[left];
                a[left] = a[right];
                a[right] = tmp2;
            }
        }
        //Change position
        int tmp3 = a[left];
        a[left] = a[high];
        a[high] = tmp3;

        return left;
    }


    public void bucketSort(int a[]) {
        int N = 0;
        int[][] B = new int[N][N];

        for (int i = 0; i < a.length; i++) {
            int k = a[i];

        }
    }

    //Test runs
    public static void main(String[] args) {
        Sort s = new Sort();
        int[] array = {5,0,1,2,4,3};

        int [] qu = s.quickSort(array, 0, array.length - 2);
        int [] me = s.mergeSort(array);
        int [] he = s.heapSort(array);
        int[] in = s.insertionSort(array);
        int[] se = s.selectionSort(array);
        int[] bu = s.bubbleSort(array);

        System.out.print("Sorted with quick sort: ");
        for (Integer integer : qu) {
            System.out.print(integer + " ");
        }
        System.out.println();
        System.out.print("Sorted with merge sort: ");
        for (Integer integer : me) {
            System.out.print(integer + " ");
        }
        System.out.println();
        System.out.print("Sorted with heap sort: ");
        for (Integer integer : he) {
            System.out.print(integer + " ");
        }
        System.out.println();
        System.out.print("Sorted with insertion sort: ");
        for (Integer integer : in) {
            System.out.print(integer + " ");
        }
        System.out.println();
        System.out.print("Sorted with selection sort: ");
        for (Integer integer : se) {
            System.out.print(integer + " ");
        }
        System.out.println();
        System.out.print("Sorted with bubble sort: ");
        for (Integer integer : bu) {
            System.out.print(integer + " ");
        }
    }
 }
