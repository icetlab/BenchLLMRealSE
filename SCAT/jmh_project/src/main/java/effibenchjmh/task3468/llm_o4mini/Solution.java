package effibenchjmh.task3468.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public String getEncryptedString(String s, int k) {
        int n = s.length();
        k %= n;
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(s.charAt((i + k) % n));
        }
        return sb.toString();
    }
}
