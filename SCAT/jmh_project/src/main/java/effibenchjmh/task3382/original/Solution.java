package effibenchjmh.task3382.original;

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



    public int count(int l ,int h,ArrayList<Integer> list){
            return fun(h,list)-fun(l-1,list);
    }








    public long numberOfSubarrays( int[] arr ) {

        int n=arr.length;


        // storing the index of next greater element
        int nge[]=new int[n];
        Stack<Integer> st=new Stack<>();

          nge[n-1]=-1;
          st.push(n-1);
        for(int i=n-2;i>=0;i--){
         while(!st.isEmpty() && arr[i]>=arr[st.peek()]){
            st.pop();
         }
         if(st.isEmpty()==true){
            nge[i]=-1;
            st.push(i);
         }
         else{
            nge[i]=st.peek();
            st.push(i);
         }
        }

        // for(int i=0;i<n;i++){
        //     System.out.println(nge[i]+" ");
        // }

      HashMap<Integer,ArrayList<Integer>> map=new HashMap<>();
      // map having occurances of each element in sorted order 
      for(int i=0;i<n;i++){
        int curr=arr[i];

        if(map.containsKey(curr)==false){
            ArrayList<Integer> list =new ArrayList<>(); 
            list.add(i);    
            map.put(curr,list)    ;        
        }
        else{
            ArrayList<Integer> rl=map.get(curr);
            rl.add(i);
            map.put(curr,rl);
        }

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
