class Solution {
  private static class Node implements Comparable<Node> {
    int val;
    int idx;
    Node(int val, int idx) { this.val = val; this.idx = idx; }
    @Override
    public int compareTo(Node o) { if (this.val != o.val) return Integer.compare(this.val, o.val); return Integer.compare(this.idx, o.idx); }
  }
  public int[] getFinalState(int[] nums, int k, int multiplier) {
    int n = nums.length;
    if (multiplier == 1) {
      return nums.clone();
    }
    int[] ans = new int[n];
    PriorityQueue<Node> minHeap = new PriorityQueue<>();
    Node[] nodes = new Node[n];
    for (int i = 0; i < n; i++) {
      nodes[i] = new Node(nums[i], i);
      minHeap.offer(nodes[i]);
    }
    for (int i = 0; i < k; i++) {
      Node cur = minHeap.poll();
      cur.val *= multiplier;
      minHeap.offer(cur);
    }
    for (Node node : nodes) {
      ans[node.idx] = node.val;
    }
    return ans;
  }
}