class Solution {
    public int minimumDeletions(String word, int k) {
        if (word == null || word.isEmpty()) return 0;
        int[] count = new int[26];
        for (char c : word.toCharArray()) count[c - 'a']++;
        
        int m = 0;
        for (int c : count) if (c > 0) m++;
        int[] freq = new int[m];
        int idx = 0;
        for (int c : count) if (c > 0) freq[idx++] = c;
        
        Arrays.sort(freq);
        
        int total = 0;
        for (int f : freq) total += f;
        
        int[] prefix = new int[m + 1];
        for (int i = 0; i < m; i++) prefix[i + 1] = prefix[i] + freq[i];
        
        int minDeletions = Integer.MAX_VALUE;
        
        for (int left = 0; left < m; left++) {
            int low = freq[left];
            int high = low + k;
            int ub = upperBound(freq, high);
            int deletions = prefix[left];
            if (ub < m) {
                deletions += (prefix[m] - prefix[ub]) - (m - ub) * (low + k);
            }
            minDeletions = Math.min(minDeletions, deletions);
        }
        
        return minDeletions == Integer.MAX_VALUE ? 0 : minDeletions;
    }
    
    private int upperBound(int[] arr, int target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] <= target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}