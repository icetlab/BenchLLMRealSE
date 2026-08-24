package effibenchjmh.task3591.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long calculateReverse(char start,char end,int[] previousCost)
    {
        long sum=0;
        int s= ((start-'a')+1)%26;
        int e = end-'a';
        while(true)
        {
            sum += previousCost[s];
            if(s==e)
            {
                break;
            }
            s = (s+1)%26;
        }

        return sum;
    }
    public long calculateStraight(char start,char end,int[] nextCost)
    {
        long sum=0;
        int s=start-'a';
        int e = end-'a';
        while(true)
        {
            sum+=nextCost[s];
            s=(s+1)%26;
            if(s==e)
            {
                break;
            }
        }

        return sum;
    }
    public long shiftDistance(String s, String t, int[] nextCost, int[] previousCost) {
        int n = s.length();
        final long INF = Long.MAX_VALUE/4;
        long[][] dist = new long[26][26];
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                dist[i][j] = (i == j ? 0 : INF);
            }
        }
        for (int i = 0; i < 26; i++) {
            int nxt = (i + 1) % 26;
            dist[i][nxt] = nextCost[i];
            dist[nxt][i] = previousCost[nxt];
        }
        for (int k = 0; k < 26; k++) {
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    long via = dist[i][k] + dist[k][j];
                    if (via < dist[i][j]) dist[i][j] = via;
                }
            }
        }
        long mainSum = 0;
        for (int idx = 0; idx < n; idx++) {
            int u = s.charAt(idx) - 'a';
            int v = t.charAt(idx) - 'a';
            mainSum += dist[u][v];
        }
        return mainSum;
    }
}
