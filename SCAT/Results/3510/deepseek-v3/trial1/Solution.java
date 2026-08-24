import java.util.Arrays;

class Solution {
    /*
    1 <= maximumHeight.length <= 105
    1 <= maximumHeight[i] <= 109
    */
    public long maximumTotalSum(int[] maximumHeight) {
      Arrays.sort(maximumHeight);
      
      long total = maximumHeight[maximumHeight.length-1];
      
      for (int i = maximumHeight.length-2; i >= 0; i--) {
        if (maximumHeight[i+1] <= maximumHeight[i]) {
          maximumHeight[i] = maximumHeight[i+1] - 1;
        }
        if (maximumHeight[i] == 0) {
            return -1;
        }
        total += maximumHeight[i];
      }
      
      return total;
    }
}