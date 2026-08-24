class Solution {
    public int incremovableSubarrayCount(int[] a) {
        int n = a.length;

        int l = 0;
        while (l + 1 < n && a[l + 1] > a[l]) {
            l++;
        }

        if (l == n - 1) { // The whole array is sorted
            return n * (n + 1) / 2;
        }

        int r = n - 1;
        while (r > 0 && a[r] > a[r - 1]) {
            r--;
        }

        long count = 0;
        
        // Case 1: Keep an empty prefix (remove a[0...j]).
        // The remaining part is a suffix a[k...n-1] where k=j+1.
        // It must be sorted, so k >= r.
        // k can be r, r+1, ..., n-1. These are n-r non-empty suffixes.
        // Plus the empty suffix (k=n).
        // Total n-r+1 options.
        count += (n - r + 1);

        // Case 2: Keep a non-empty prefix a[0...i], where 0 <= i <= l.
        for (int i = 0; i <= l; i++) {
            int val = a[i];
            // Find the first index p in the sorted suffix part [r, n-1]
            // such that a[p] > val.
            int p = findFirstGreater(a, r, n - 1, val);
            
            // Any k from p to n-1 can start a valid suffix.
            // Number of non-empty suffixes is n - p.
            // Plus 1 for the empty suffix.
            count += (n - p) + 1;
        }

        return (int) count;
    }

    private int findFirstGreater(int[] a, int start, int end, int target) {
        int low = start, high = end;
        // If not found, ans will be end + 1, which is correct for calculation.
        int ans = end + 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (a[mid] > target) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }
}