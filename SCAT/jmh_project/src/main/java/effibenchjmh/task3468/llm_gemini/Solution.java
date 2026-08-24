package effibenchjmh.task3468.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public String getEncryptedString(String s, int k) {
        int n = s.length();
        if (n == 0) {
            return s;
        }
        k %= n;
        return s.substring(k) + s.substring(0, k);
    }
}
