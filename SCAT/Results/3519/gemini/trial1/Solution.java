class Solution {
    public int winningPlayerCount(int n, int[][] pick) {
        if (pick == null || pick.length == 0) {
            return 0;
        }

        java.util.HashSet<Integer> winners = new java.util.HashSet<>();
        HashMap<Integer, HashMap<Integer, Integer>> playerPicks = new HashMap<>();

        for (int[] p : pick) {
            int player = p[0];
            int item = p[1];

            if (winners.contains(player)) {
                continue;
            }

            HashMap<Integer, Integer> itemCounts = playerPicks.computeIfAbsent(player, k -> new HashMap<>());
            int newCount = itemCounts.merge(item, 1, Integer::sum);

            if (newCount >= player + 1) {
                winners.add(player);
            }
        }

        return winners.size();
    }
}