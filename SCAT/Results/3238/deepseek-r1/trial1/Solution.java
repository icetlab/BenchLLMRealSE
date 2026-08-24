import java.util.*;

class MyBean {
    public Integer word;
    public long distance;

    public MyBean(Integer word, long distance) {
        this.word = word;
        this.distance = distance;
    }
}

class TrieNode {
    public Map<Integer, TrieNode> childMap;
    public boolean isEndOfWord;
    public int index;

    public TrieNode() {
        childMap = new HashMap<>();
        isEndOfWord = false;
        index = -1;
    }
}

class Solution {
    private Map<Integer, long[]> distCache = new HashMap<>();
    private static final int MAX_SUB_LEN = 100;

    public long minimumCost(String source, String target, String[] original, String[] changed, int[] cost) {
        distCache.clear();
        int n = original.length;
        TrieNode root = new TrieNode();
        int[] currentIndex = new int[]{0};

        // insert all words (both original and changed) into trie
        for (int i = 0; i < n; i++) {
            insert(root, original[i], currentIndex);
            insert(root, changed[i], currentIndex);
        }
        int N = currentIndex[0];

        // build adjacency list for all vertices
        List<int[]>[] adj = new List[N];
        for (int i = 0; i < N; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int u = findIndex(root, original[i]);
            int v = findIndex(root, changed[i]);
            adj[u].add(new int[]{v, cost[i]});
        }

        long[] dp = new long[source.length() + 1];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[source.length()] = 0;

        for (int i = source.length() - 1; i >= 0; i--) {
            TrieNode ptr1 = root;
            TrieNode ptr2 = root;
            dp[i] = Long.MAX_VALUE;
            boolean equal = true;
            boolean ptr1Found = true;
            boolean ptr2Found = true;

            int limit = Math.min(i + MAX_SUB_LEN, source.length());
            for (int j = i; j < limit; j++) {
                int c1 = source.charAt(j);
                int c2 = target.charAt(j);

                if (ptr1.childMap.containsKey(c1)) {
                    ptr1 = ptr1.childMap.get(c1);
                } else {
                    ptr1Found = false;
                }
                if (ptr2.childMap.containsKey(c2)) {
                    ptr2 = ptr2.childMap.get(c2);
                } else {
                    ptr2Found = false;
                }

                if (c1 != c2) {
                    equal = false;
                }

                if (equal) {
                    if (dp[j + 1] != Long.MAX_VALUE) {
                        dp[i] = Math.min(dp[i], dp[j + 1]);
                    }
                } else if (ptr1Found && ptr2Found) {
                    if (ptr1.isEndOfWord && ptr2.isEndOfWord) {
                        long[] dist = getDistances(adj, ptr1.index);
                        long d = dist[ptr2.index];
                        if (d != Long.MAX_VALUE && dp[j + 1] != Long.MAX_VALUE) {
                            dp[i] = Math.min(dp[i], d + dp[j + 1]);
                        }
                    }
                } else {
                    break;
                }
            }
        }

        return dp[0] == Long.MAX_VALUE ? -1 : dp[0];
    }

    private long[] getDistances(List<int[]>[] adj, int src) {
        if (distCache.containsKey(src)) {
            return distCache.get(src);
        }
        int N = adj.length;
        long[] dist = new long[N];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[src] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.offer(new long[]{0, src});

        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            long d = top[0];
            int u = (int) top[1];
            if (d != dist[u]) continue;
            for (int[] edge : adj[u]) {
                int v = edge[0];
                long nd = d + edge[1];
                if (nd < dist[v]) {
                    dist[v] = nd;
                    pq.offer(new long[]{nd, v});
                }
            }
        }

        distCache.put(src, dist);
        return dist;
    }

    public void insert(TrieNode root, String word, int[] index) {
        TrieNode ptr = root;
        for (int i = 0; i < word.length(); i++) {
            int ch = word.charAt(i);
            if (!ptr.childMap.containsKey(ch)) {
                ptr.childMap.put(ch, new TrieNode());
            }
            ptr = ptr.childMap.get(ch);
        }
        ptr.isEndOfWord = true;
        if (ptr.index == -1) {
            ptr.index = index[0]++;
        }
    }

    public int findIndex(TrieNode root, String word) {
        TrieNode ptr = root;
        for (int i = 0; i < word.length(); i++) {
            ptr = ptr.childMap.get((int) word.charAt(i));
        }
        return ptr.index;
    }
}