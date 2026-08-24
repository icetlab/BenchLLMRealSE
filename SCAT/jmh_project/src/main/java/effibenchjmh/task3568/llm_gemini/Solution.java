package effibenchjmh.task3568.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int generateKey(int num1, int num2, int num3) {
      // Thousands digit
      int min_1000 = Math.min(num1 / 1000, Math.min(num2 / 1000, num3 / 1000));

      // Hundreds digit
      int min_100 = Math.min((num1 / 100) % 10, Math.min((num2 / 100) % 10, (num3 / 100) % 10));

      // Tens digit
      int min_10 = Math.min((num1 / 10) % 10, Math.min((num2 / 10) % 10, (num3 / 10) % 10));

      // Ones digit
      int min_1 = Math.min(num1 % 10, Math.min(num2 % 10, num3 % 10));

      return min_1000 * 1000 + min_100 * 100 + min_10 * 10 + min_1;
    }
}
