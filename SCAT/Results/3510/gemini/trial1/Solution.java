class Solution {
    /*
    1 <= maximumHeight.length <= 105
    1 <= maximumHeight[i] <= 109
    */
    public long maximumTotalSum(int[] maximumHeight) {
      java.util.Arrays.sort(maximumHeight);
      
      for (int i = maximumHeight.length-1; i > 0; i--) {
        if (maximumHeight[i] <= maximumHeight[i-1]) {
          maximumHeight[i-1] = maximumHeight[i] - 1;
        }
      }

      long total = 0;
      for (int height : maximumHeight) {
        if (height == 0) {
            return -1;
        }
        total += height;
      }
      return total;
    }
}