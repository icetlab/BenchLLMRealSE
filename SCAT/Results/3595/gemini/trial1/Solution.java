class Solution {
    public boolean isPossibleToRearrange(String s, String t, int k) {
        if(s.length()%k!=0 || s.length()!=t.length()){
            return false;
        }

        java.util.Map<String, Integer> blockCounts = new java.util.HashMap<>();
        int blockSize = s.length() / k;

        for (int i = 0; i < s.length(); i += blockSize) {
            String block = s.substring(i, i + blockSize);
            blockCounts.put(block, blockCounts.getOrDefault(block, 0) + 1);
        }

        for (int i = 0; i < t.length(); i += blockSize) {
            String block = t.substring(i, i + blockSize);
            int count = blockCounts.getOrDefault(block, 0);
            if (count == 0) {
                return false;
            }
            blockCounts.put(block, count - 1);
        }

        return true;
    }
}