class Solution {
    public int minimumOperationsToMakeKPeriodic(String word, int k) {
        int n = word.length();
        int blocks = n / k;
        Map<String, Integer> freq = new HashMap<>();
        int maxFreq = 0;
        for (int i = 0; i < n; i += k) {
            String block = word.substring(i, i + k);
            int count = freq.merge(block, 1, Integer::sum);
            if (count > maxFreq) {
                maxFreq = count;
            }
        }
        return blocks - maxFreq;
    }
}