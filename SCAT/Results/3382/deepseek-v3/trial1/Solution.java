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
            if(st.isEmpty()){
                nge[i]=-1;
                st.push(i);
            } else {
                nge[i]=st.peek();
                st.push(i);
            }
        }

        // map having occurrences of each element in sorted order
        HashMap<Integer,ArrayList<Integer>> map=new HashMap<>();
        for(int i=0;i<n;i++){
            int curr=arr[i];
            ArrayList<Integer> list=map.get(curr);
            if(list==null){
                list=new ArrayList<>();
                map.put(curr,list);
            }
            list.add(i);
        }

        long ans=0;

        // For each value, process its occurrence list with two pointers.
        // For each occurrence i, we need count of occurrences in [i, nge[i)-1].
        // Since occurrences are sorted, we can maintain a right pointer for each value.
        for(ArrayList<Integer> list : map.values()){
            int m=list.size();
            int r=0; // first index in list with position > current right bound
            for(int idx=0; idx<m; idx++){
                int i=list.get(idx);
                int ngei=nge[i];
                int h = (ngei==-1) ? n-1 : ngei-1;
                // advance r while list.get(r) <= h
                while(r<m && list.get(r)<=h){
                    r++;
                }
                // occurrences from idx to r-1 are within [i,h]
                ans += (r - idx);
            }
        }

        return ans;
    }

}