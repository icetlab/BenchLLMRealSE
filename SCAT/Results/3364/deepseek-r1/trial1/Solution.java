class Solution {
    
    int MAX = Integer.MAX_VALUE/2;
    HashMap<Long, Integer> dp;

    public int minimumValueSum(int[] nums, int[] andValues) {

        int n = nums.length;
        dp = new HashMap<>();
        int sum = getSum(0, (1<<19) - 1, 0, nums, andValues);
        return (sum==MAX)?-1:sum;
        
    }

    private int getSum(int i, int and, int j, int[] nums, int[] andValues) {

        int n = nums.length;
        int m = andValues.length;

        int ans = MAX;
        if(i==n && j==m) return 0;
        if(i==n || j==m) return MAX;

        long key = ((long)i << 40) | ((long)j << 20) | (and & 0xFFFFF);
        if(dp.containsKey(key)) return dp.get(key);

        if((and & nums[i]) < andValues[j] ) return MAX;

        //pick the current and check stop and update answer.

        if((and & nums[i]) == andValues[j]) {
            ans = Math.min(nums[i] + getSum(i+1, (1<<19) - 1, j+1, nums, andValues), ans);
        } 

        //pick the current and continue
        ans = Math.min(ans, getSum(i+1, and&nums[i], j, nums, andValues));

        dp.put(key, ans);
        return ans;
    }
}

/*


 |
 * * * * * * * * * *
           |

 * * * *
   |

   n*m*and

*/