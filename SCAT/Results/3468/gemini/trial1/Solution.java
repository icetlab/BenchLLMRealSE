class Solution {
    public String getEncryptedString(String s, int k) {
        int n = s.length();
        if (n == 0) {
            return s;
        }
        k %= n;
        return s.substring(k) + s.substring(0, k);
    }
}