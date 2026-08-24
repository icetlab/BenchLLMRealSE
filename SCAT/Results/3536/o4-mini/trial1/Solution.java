class Solution {
    public int countOfPairs(int[] nums) {
        int n=nums.length;
        int mod=1000000007;
        int[] next = new int[1001];
        int[] curr = new int[1001];
        for(int j=0;j<=nums[n-1];j++) next[j]=1;
        for(int j=1;j<=1000;j++) next[j]=(next[j]+next[j-1])%mod;
        for(int i=n-2;i>=0;i--){
            for(int k=0;k<=1000;k++) curr[k]=0;
            for(int j=0;j<=nums[i];j++){
                int l=Math.max(j,nums[i+1]-nums[i]+j);
                int h=nums[i+1];
                int val = next[h];
                if(l>0) val = (val - next[l-1] + mod)%mod;
                curr[j] = val;
            }
            for(int j=1;j<=1000;j++){
                curr[j] = (curr[j] + curr[j-1])%mod;
            }
            int[] tmp = next;
            next = curr;
            curr = tmp;
        }
        return next[1000];
    }
}