class Solution {
    public int minimumOperationsToMakeKPeriodic(String word, int k) {
        
        java.util.Map<String, Integer> freq = new java.util.HashMap<>();
        int maxFreq = 0;
        for (int i = 0; i + k <= word.length(); i += k) {
            String part = word.substring(i, i + k);
            int count = freq.merge(part, 1, Integer::sum);
            if (count > maxFreq) {
                maxFreq = count;
            }
        }
        return word.length() / k - Math.max(maxFreq, 1);
    }


}