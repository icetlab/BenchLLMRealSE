class Solution {
    public String getEncryptedString(String s, int k) {
        int n=s.length();
        char[] result = new char[n];
        for(int i=0;i<n;i++){
            result[i]=s.charAt((i+k)%n);
        }
        return new String(result);
    }
}