class Solution {
    public int generateKey(int num1, int num2, int num3) {
      int result = 0;
      int divisor = 1000;
      for (int i = 0; i < 4; i++) {
        int digit1 = (num1 / divisor) % 10;
        int digit2 = (num2 / divisor) % 10;
        int digit3 = (num3 / divisor) % 10;
        int minDigit = Math.min(digit1, Math.min(digit2, digit3));
        result = result * 10 + minDigit;
        divisor /= 10;
      }
      return result;
      
        
        
    }
}