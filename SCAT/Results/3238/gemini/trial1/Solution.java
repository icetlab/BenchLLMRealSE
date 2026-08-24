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
    public long minimumCost(String source, String target, String[] original, String[] changed, int[] cost) {
        Map<Integer, List<MyBean>> adjMap = new HashMap<>();
        int n = original.length;
        TrieNode root = new TrieNode();
        int currentIndex[] = new int[]{0};

        for (int i = 0; i < n; i++) {
            insert(root, original[i], currentIndex);
            insert(root, changed[i], currentIndex);
        }

        for (int i = 0; i < n; i++) {
            int originalIndex = findIndex(root, original[i]);
            if (!adjMap.containsKey(originalIndex)) {
                adjMap.put(originalIndex, new ArrayList<>());
            }
            adjMap.get(originalIndex).add(new MyBean(findIndex(root, changed[i]), (long)cost[i]));
        }
        Map<Integer, Map<Integer, MyBean>> distanceMap = new HashMap<>(); 

        for (int vertex : adjMap.keySet()) {
            distanceMap.put(vertex, findDistances(adjMap, vertex));
        }

        long dp[] = new long[source.length() + 1];

        dp[source.length()] = (long)(0);

        for (int i = source.length() - 1; i >= 0; i--) {
            TrieNode ptr1 = root;
            TrieNode ptr2 = root;
            dp[i] = Long.MAX_VALUE;
            boolean equal = true;
            boolean ptr1Found = true;
            boolean ptr2Found = true;

            for (int j = i; j <= Math.min(i + 100, source.length() - 1); j++) {
                if (ptr1.childMap.containsKey((int)source.charAt(j))) {
                    ptr1 = ptr1.childMap.get((int)source.charAt(j));
                } else {
                    ptr1Found = false;
                }

                if (ptr2.childMap.containsKey((int)target.charAt(j))) {
                    ptr2 = ptr2.childMap.get((int)target.charAt(j));
                } else {
                    ptr2Found = false;
                }
                if (source.charAt(j) != target.charAt(j)) {
                    equal = false;
                }


                if (equal) {
                    if (dp[j + 1] != Long.MAX_VALUE) {
                        dp[i] = Math.min(dp[i], dp[j + 1]);
                    }
                } else if (ptr1Found && ptr2Found) {
                    if (ptr1.isEndOfWord && ptr2.isEndOfWord && distanceMap.containsKey(ptr1.index) && distanceMap.get(ptr1.index).containsKey(ptr2.index) && dp[j + 1] != Long.MAX_VALUE) {
                        dp[i] = Math.min(dp[i], distanceMap.get(ptr1.index).get(ptr2.index).distance + dp[j + 1]);
                        
                    }
                } else {
                    break;
                }

                
            }
        }

        return (dp[0] == Long.MAX_VALUE) ? -1 : dp[0];
        
    }

    public Map<Integer, MyBean> findDistances(Map<Integer, List<MyBean>> adjMap, int source) {
        TreeSet<MyBean> ts = new TreeSet<>((b1, b2) -> {
            if (Long.compare(b1.distance, b2.distance) != 0) {
                return Long.compare(b1.distance, b2.distance);
            } else {
                return Integer.compare(b1.word, b2.word);
            }
        });

        Map<Integer, MyBean> distanceMap = new HashMap<>();

        distanceMap.put(source, new MyBean(source, (long)0));

        ts.add(distanceMap.get(source));

        while (!ts.isEmpty()) {
            MyBean top = ts.pollFirst();

            for (MyBean adjBean : adjMap.getOrDefault(top.word, new ArrayList<>())) {
                MyBean adjDistance = distanceMap.getOrDefault(adjBean.word, new MyBean(adjBean.word, Long.MAX_VALUE));

                if (top.distance + adjBean.distance < adjDistance.distance) {
                    ts.remove(adjDistance);
                    adjDistance.distance = top.distance + adjBean.distance;
                    ts.add(adjDistance);
                    distanceMap.put(adjBean.word, adjDistance);
                }
            }
        }

        return distanceMap;
    }

    public void insert(TrieNode root, String word, int index[]) {
        TrieNode ptr = root;

        for (int i = 0; i < word.length(); i++) {
            if (!ptr.childMap.containsKey((int)(word.charAt(i)))) {
                ptr.childMap.put((int)(word.charAt(i)), new TrieNode());
            }

            ptr = ptr.childMap.get((int)(word.charAt(i)));
        }

        ptr.isEndOfWord = true;

        if (ptr.index == -1) {
            ptr.index = index[0]++;
        }
    }

    public int findIndex(TrieNode root, String word) {
        TrieNode ptr = root;

        for (int i = 0; i < word.length(); i++) {
            ptr = ptr.childMap.get((int)(word.charAt(i)));
        }

        return ptr.index;
    }
}