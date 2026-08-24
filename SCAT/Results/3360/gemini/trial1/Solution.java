class Solution {
    public int minimumDeletions(String word, int k) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        List<Integer> l = new ArrayList<>(map.values());
        Collections.sort(l);
        if (l.isEmpty()) {
            return 0;
        }

        int minDeletions = Integer.MAX_VALUE;
        int prefixSum = 0;
        for (int i = 0; i < l.size(); i++) {
            int targetMax = l.get(i) + k;
            int suffixDeletions = 0;
            for (int j = i + 1; j < l.size(); j++) {
                if (l.get(j) > targetMax) {
                    suffixDeletions += l.get(j) - targetMax;
                }
            }
            minDeletions = Math.min(minDeletions, prefixSum + suffixDeletions);
            prefixSum += l.get(i);
        }

        return minDeletions;

    }
}