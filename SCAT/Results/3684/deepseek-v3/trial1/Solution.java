class Solution {
    public boolean hasMatch(String str, String subStr) {
        int starIdx = subStr.indexOf('*');
        String prefix = subStr.substring(0, starIdx);
        String suffix = subStr.substring(starIdx + 1);
        int prefixPos = str.indexOf(prefix);
        if (prefixPos == -1) return false;
        int suffixStart = prefixPos + prefix.length();
        return str.indexOf(suffix, suffixStart) != -1;
    }
}