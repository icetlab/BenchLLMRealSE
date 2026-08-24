package effibenchjmh.task3468;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public String getEncryptedString(String s, int k) {
        int n = s.length();
        k = k % n;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            result.append(s.charAt((i + k) % n));
        }
        return result.toString();
    }
}
