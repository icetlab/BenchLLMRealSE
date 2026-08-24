class Solution {

    private int fun(int i, ArrayList<Integer> list){
    int insertionPoint = java.util.Collections.binarySearch(list, i);
    if (insertionPoint >= 0) {
        return insertionPoint + 1;
    } else {
        return -(insertionPoint + 1);
    }
}



    private int count(int l ,int h,ArrayList<Integer> list){
            return fun(h,list)-fun(l-1,list);
    }








    public long numberOfSubarrays( int[] arr ) {

        int n=arr.length;


        // storing the index of next greater element
        int nge[]=new int[n];
        java.util.Deque<Integer> st=new java.util.ArrayDeque<>();

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

        map.computeIfAbsent(curr, k -> new ArrayList<>()).add(i);

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