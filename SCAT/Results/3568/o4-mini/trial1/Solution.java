class Solution {
    public int generateKey(int num1, int num2, int num3) {
      int[] divs = {1000, 100, 10, 1};
      int result = 0;
      for (int d : divs) {
        int a = (num1 / d) % 10;
        int b = (num2 / d) % 10;
        int c = (num3 / d) % 10;
        int min = a;
        if (b < min) min = b;
        if (c < min) min = c;
        result = result * 10 + min;
      }
      return result;
    }
}