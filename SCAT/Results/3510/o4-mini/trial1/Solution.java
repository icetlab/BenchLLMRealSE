import java.util.Arrays;

class Solution {
    // recursive call
    public void mergeSort(int[] arr, int l, int r) {
      if (l < r) {
        int m = l + (r-l)/2; // mid

        mergeSort(arr, l, m); // splits left partition
        mergeSort(arr, m+1, r); // splits right partition
        merge(arr, l, m, r); // combine them with merge
      }
    }
    
    // intermediate function
    public void merge(int[] arr, int l, int m, int r) {
      int l_n = m - l + 1; // left partition index cap - minus l + 1
      int r_n = r - m; // right partition index cap
      int arr_left[] = new int[l_n];
      int arr_right[] = new int[r_n];
      
      // copies data from partitions
      for (int i = 0; i < l_n; i++) {
        arr_left[i] = arr[l + i];
      }

      for (int j = 0; j < r_n; j++) {
        arr_right[j] = arr[(m + 1) + j];
      }

      // merge them
      int i = 0; // left partition
      int j = 0; // right partition
      int k = l; // array index - not 1

      while (i < l_n && j < r_n) { // if either not true, means that partition is done
      // [1] vs [2,3] - [1,2,3]
        if (arr_left[i] <= arr_right[j]) {
          arr[k] = arr_left[i];
          i++;
        } else {
          arr[k] = arr_right[j];
          j++;
        }
        k++;
      }

      // remaining elements from either side
      while (i < l_n) {
        arr[k] = arr_left[i];
        i++;
        k++;
      }

      while (j < r_n) {
        arr[k] = arr_right[j];
        j++;
        k++;
      }
    }

    /*
    1 <= maximumHeight.length <= 105
    1 <= maximumHeight[i] <= 109
    */
    public long maximumTotalSum(int[] maximumHeight) {
      Arrays.sort(maximumHeight);
      
      for (int i = maximumHeight.length-1; i > 0; i--) {
        if (maximumHeight[i] <= maximumHeight[i-1]) {
          maximumHeight[i-1] = maximumHeight[i] - 1;
        }
      }

      long total = 0;
      for (int i : maximumHeight) {
        if (i == 0) {
            return -1;
        }
        total += i;
      }
      return total;
    }
}