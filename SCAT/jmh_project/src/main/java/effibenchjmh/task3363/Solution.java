package effibenchjmh.task3363;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long[] mostFrequentIDs(int[] arr, int[] freq) {
        int n = arr.length;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a,b)->(int)(b[1]-a[1]));
        HashMap<Long,Long> hp = new HashMap<>();
        
        long ans[] = new long[n];
        
        for(int i=0; i<n; i++){
            long x = arr[i];
            long c = freq[i];
            hp.put(x,hp.getOrDefault(x,(long)0)+c);
            pq.add(new long[]{x,hp.get(x)});
            long a[] = pq.remove();
            while(a[1]!=hp.get(a[0])){
                a = pq.remove();
            }
            ans[i] = a[1];
            pq.add(a);
        }
        return ans;
    }
}
