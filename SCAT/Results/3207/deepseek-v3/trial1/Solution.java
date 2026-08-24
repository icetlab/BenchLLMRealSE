class Solution {
    public int findMinimumOperations(String s1, String s2, String s3) {
        int minLen = Math.min(s1.length(), Math.min(s2.length(), s3.length()));
        int commonPrefixLen = 0;
        
        for (int i = 0; i < minLen; i++) {
            if (s1.charAt(i) == s2.charAt(i) && s2.charAt(i) == s3.charAt(i)) {
                commonPrefixLen++;
            } else {
                break;
            }
        }
        
        if (commonPrefixLen == 0) {
            return -1;
        }
        
        return (s1.length() - commonPrefixLen) + 
               (s2.length() - commonPrefixLen) + 
               (s3.length() - commonPrefixLen);
    }
}