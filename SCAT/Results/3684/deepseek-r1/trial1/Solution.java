class Solution {
    public boolean hasMatch(String str, String subStr) {
        int star = subStr.indexOf('*');
        if (star < 0) {
            return false;
        }

        String prefix = subStr.substring(0, star);
        String suffix = subStr.substring(star + 1);

        int prefixIndex = str.indexOf(prefix);
        if (prefixIndex < 0) {
            return false;
        }

        int suffixStart = prefixIndex + prefix.length();
        return str.indexOf(suffix, suffixStart) != -1;
    }
}