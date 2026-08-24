class Solution {
    public int findMinimumOperations(String s1, String s2, String s3) {
        // If all strings are empty, no operations needed (edge case)
        if (s1.isEmpty() && s2.isEmpty() && s3.isEmpty()) {
            return 0;
        }

        // Compute the length of the longest common prefix (LCP)
        int minLen = Math.min(s1.length(), Math.min(s2.length(), s3.length()));
        int prefixLen = 0;
        while (prefixLen < minLen
                && s1.charAt(prefixLen) == s2.charAt(prefixLen)
                && s2.charAt(prefixLen) == s3.charAt(prefixLen)) {
            prefixLen++;
        }

        // If no common prefix and strings exist, cannot make equal without emptying one
        if (prefixLen == 0) {
            return -1;
        }

        // Total deletions = (len1 - prefixLen) + (len2 - prefixLen) + (len3 - prefixLen)
        return (s1.length() - prefixLen)
             + (s2.length() - prefixLen)
             + (s3.length() - prefixLen);
    }
}