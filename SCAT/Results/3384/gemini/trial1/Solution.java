class Solution {
    public int minimumOperationsToMakeKPeriodic(String word, int k) {
        HashMap<String, Integer> counts = new HashMap<>();
        int maxFreq = 0;

        for (int i = 0; i < word.length(); i += k) {
            String sub = word.substring(i, i + k);
            int newCount = counts.getOrDefault(sub, 0) + 1;
            counts.put(sub, newCount);
            if (newCount > maxFreq) {
                maxFreq = newCount;
            }
        }

        int totalParts = word.length() / k;
        return totalParts - maxFreq;
    }


}