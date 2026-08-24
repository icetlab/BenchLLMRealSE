class Solution {
    public int countKConstraintSubstrings(String s, int k) {
        int n = s.length();
        int zeros = 0;
        int ones = 0;
        int left = 0;
        int count = 0;
        for (int right = 0; right < n; right++) {
            if (s.charAt(right) == '0') {
                zeros++;
            } else {
                ones++;
            }
            while (zeros > k && ones > k) {
                if (s.charAt(left) == '0') {
                    zeros--;
                } else {
                    ones--;
                }
                left++;
            }
            count += right - left + 1;
        }
        return count;
    }
}