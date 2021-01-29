
public class BinarySearch {

    private static int[] sortedArray;

    public BinarySearch(int[] array) {
        sortedArray = array;
    }

    public static boolean search(int x) {
        int low = 0;
        int high = sortedArray.length - 1;

        while (low <= high) {
            int i = (low+high) / 2;

            if (sortedArray[i] == x) {
                return true;
            }
            else if (sortedArray[i] < x) {
                low = i+1;
            }
            else if (sortedArray[i] > x) {
                high = i-1;
            }
        }
        return false;
    }

    //Finds missing int in integer series array (eks. [0, 1, 4, 3] -> 2 missing)
    private int findMissingInt(int[] a) {
        int n = a.length + 1;
        int sumAll = (n * (n-1)) / 2;
        int sumArray = 0;
        for (int i = 0; i < a.length; i++) {
            sumArray = sumArray + a[i];
        }
        return sumAll - sumArray;
    }

    //Test run
    public static void main (String[]args) {
        int[] arraySorted = new int[20];
        for (int i = 0; i < 20; i++) {
            arraySorted[i++] = i;
        }

        System.out.println(search(4) + " is present in the array.");

        BinarySearch bs = new BinarySearch(arraySorted);
        int[] a = new int[5];
        a[0] = 3;
        a[1] = 0;
        a[2] = 1;
        a[3] = 5;
        a[4] = 4;
        System.out.println("Missing number is " + bs.findMissingInt(a));
    }
}



