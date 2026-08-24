class Solution {
    public boolean hasMatch(String str, String subStr) {
         int startIndex = subStr.indexOf("*");
         String prefix = subStr.substring(0,startIndex);
         String suffix = subStr.substring(startIndex+1);
         int idx = str.indexOf(prefix);
         if (idx < 0) return false;
         int from = idx + prefix.length();
         if (suffix.isEmpty()) return true;
         return str.indexOf(suffix, from) >= 0;
    }
}