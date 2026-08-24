package effibenchjmh.task3570.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countOfSubstrings(String word, int k) {
        int n = word.length();
        // preprocess next occurrence of each vowel
        int[] nextA = new int[n+1], nextE = new int[n+1], nextI = new int[n+1], nextO = new int[n+1], nextU = new int[n+1];
        int INF = n;
        nextA[n] = nextE[n] = nextI[n] = nextO[n] = nextU[n] = INF;
        for (int i = n - 1; i >= 0; i--) {
            char c = word.charAt(i);
            nextA[i] = (c == 'a' ? i : nextA[i + 1]);
            nextE[i] = (c == 'e' ? i : nextE[i + 1]);
            nextI[i] = (c == 'i' ? i : nextI[i + 1]);
            nextO[i] = (c == 'o' ? i : nextO[i + 1]);
            nextU[i] = (c == 'u' ? i : nextU[i + 1]);
        }
        // collect consonant positions
        java.util.List<Integer> consList = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            char c = word.charAt(i);
            if (c!='a' && c!='e' && c!='i' && c!='o' && c!='u') {
                consList.add(i);
            }
        }
        int consSize = consList.size();
        int idx = 0;
        long sumK = 0, sumK1 = 0;
        for (int i = 0; i < n; i++) {
            while (idx < consSize && consList.get(idx) < i) idx++;
            int jV = nextA[i]; jV = Math.max(jV, nextE[i]); jV = Math.max(jV, nextI[i]); jV = Math.max(jV, nextO[i]); jV = Math.max(jV, nextU[i]);
            if (jV == INF) continue;
            int boundK = (idx + k < consSize) ? consList.get(idx + k) - 1 : n - 1;
            int cntK = boundK - jV + 1;
            if (cntK > 0) sumK += cntK;
            if (k > 0) {
                int boundK1 = (idx + k - 1 < consSize) ? consList.get(idx + k - 1) - 1 : n - 1;
                int cntK1 = boundK1 - jV + 1;
                if (cntK1 > 0) sumK1 += cntK1;
            }
        }
        long result = (k > 0 ? sumK - sumK1 : sumK);
        return (int) result;
    }
}
