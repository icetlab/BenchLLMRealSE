class Solution {
    public int incremovableSubarrayCount(int[] a) {
        int n = a.length;
        int count = 0;
        
        // Precompute longest strictly increasing prefix ending at each index
        int[] prefix = new int[n];
        prefix[0] = 1;
        for (int i = 1; i < n; i++) {
            prefix[i] = (a[i] > a[i-1]) ? prefix[i-1] + 1 : 1;
        }
        
        // Precompute longest strictly increasing suffix starting at each index
        int[] suffix = new int[n];
        suffix[n-1] = 1;
        for (int i = n-2; i >= 0; i--) {
            suffix[i] = (a[i] < a[i+1]) ? suffix[i+1] + 1 : 1;
        }
        
        // Count subarrays that can be removed
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                // Check if prefix before i is strictly increasing
                boolean leftValid = (i == 0) || (prefix[i-1] == i);
                // Check if suffix after j is strictly increasing
                boolean rightValid = (j == n-1) || (suffix[j+1] == n - j - 1);
                // Check if the boundary between left and right parts is valid
                boolean boundaryValid = (i == 0 || j == n-1) || (a[i-1] < a[j+1]);
                
                if (leftValid && rightValid && boundaryValid) {
                    count++;
                }
            }
        }
        
        return count;
    }
}