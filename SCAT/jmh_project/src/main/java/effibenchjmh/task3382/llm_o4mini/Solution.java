package effibenchjmh.task3382.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {

  public int fun(int i, ArrayList<Integer> list){
    int l = 0;
    int h = list.size() - 1;
    int ans = -1;
    while (l <= h) {
        int m = (l + h) / 2;
        if (list.get(m) <= i) {
            ans = m;    
            l = m + 1;   
        } else {
            h = m - 1;
        }
    }
    return ans + 1;  
}



    public int count(int l, int h, ArrayList<Integer> list) {
        int left = java.util.Collections.binarySearch(list, l);
        if (left < 0) left = -left - 1;
        int right = java.util.Collections.binarySearch(list, h);
        if (right < 0) right = -right - 2;
        return right >= left ? right - left + 1 : 0;
    }








    public long numberOfSubarrays( int[] arr ) {

        int n=arr.length;


        // storing the index of next greater element
        int[] nge = new int[n];
        java.util.Deque<Integer> st = new java.util.ArrayDeque<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && arr[i] >= arr[st.peek()]) {
                st.pop();
            }
            nge[i] = st.isEmpty() ? -1 : st.peek();
            st.push(i);
        }

        // for(int i=0;i<n;i++){
        //     System.out.println(nge[i]+" ");
        // }

      java.util.HashMap<Integer, ArrayList<Integer>> map = new java.util.HashMap<>();
      for (int i = 0; i < n; i++) {
          map.computeIfAbsent(arr[i], k -> new java.util.ArrayList<>()).add(i);
      }

      long ans=0;

        for(int i=0;i<n;i++){
            int curr=arr[i];
            ArrayList<Integer> list=map.get(curr);
            int ngei=nge[i];

            int l=i;
            int h=0;
            if(ngei==-1){
                h=n-1;
            }
            else{
                h=ngei-1;
            }
            if(l==h){
                ans+=1;
                continue;
            }else{
            // calculate number of indices between l and h botj inclusive
         int   cnt=count(l,h,list);
         ans+=(cnt);
            }

        }





   return ans; }

}
