package effibenchjmh.task3245;

import java.util.*;
import java.util.stream.*;

class Solution {
    public List<Integer> beautifulIndices(String s, String a, String b, int k) {
        List<Integer> result = new ArrayList<>();

        List<Integer> indicesA = new ArrayList<>();
        List<Integer> indicesB = new ArrayList<>();

        for (int i = 0; i <= s.length() - a.length(); i++) {
            if (s.substring(i, i + a.length()).equals(a)) {
                indicesA.add(i);
            }
        }
            
        for (int i = 0; i <= s.length() - b.length(); i++) {
            if (s.substring(i, i + b.length()).equals(b)) {
                indicesB.add(i);
            }
        }

        for (int i : indicesA) {
            int low = 0, high = indicesB.size() - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                int j = indicesB.get(mid);

                if (Math.abs(i - j) <= k) {
                    result.add(i);
                    break;
                } else if (j < i) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }

        return result;
    }
}
