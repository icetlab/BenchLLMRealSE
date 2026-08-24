class Solution {
    
    static final int MAX = Integer.MAX_VALUE / 2;
    static final int ALL_ONES = (1 << 19) - 1;
    HashMap<Long, Integer> dp;

    public int minimumValueSum(int[] nums, int[] andValues) {

        int n = nums.length;
        dp = new HashMap<>();
        int sum = getSum(0, ALL_ONES, 0, nums, andValues);
        return (sum==MAX)?-1:sum;
        
    }

    private int getSum(int i, int and, int j, int[] nums, int[] andValues) {

        int n = nums.length;
        int m = andValues.length;

        int ans = MAX;
        if(i==n && j==m) return 0;
        if(i==n || j==m) return MAX;

        long key = ((long) i << 33) | ((long) j << 19) | and;
        if (dp.containsKey(key)) {
            return dp.get(key);
        }

        int currentAnd = and & nums[i];

        // Pruning: if current AND can't possibly become andValues[j]
        if ((currentAnd & andValues[j]) != andValues[j]) {
            dp.put(key, MAX);
            return MAX;
        }

        // Option 1: End the current segment with nums[i]
        if (currentAnd == andValues[j]) {
            ans = Math.min(ans, nums[i] + getSum(i + 1, ALL_ONES, j + 1, nums, andValues));
        }

        // Option 2: Continue the current segment with nums[i]
        ans = Math.min(ans, getSum(i + 1, currentAnd, j, nums, andValues));

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