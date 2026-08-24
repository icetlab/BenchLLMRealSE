class Solution {
  public int[] getFinalState(int[] nums, int k, int multiplier) {
    int n = nums.length;
    int[] ans = new int[n];
    
    // Min-heap storing [value, index] pairs using primitive ints
    Queue<int[]> minHeap = new PriorityQueue<>((a, b) -> 
        a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
    
    for (int i = 0; i < n; i++) {
      minHeap.offer(new int[]{nums[i], i});
    }
    
    while (k-- > 0) {
      int[] top = minHeap.poll();
      top[0] *= multiplier;
      minHeap.offer(top);
    }
    
    while (!minHeap.isEmpty()) {
      int[] top = minHeap.poll();
      ans[top[1]] = top[0];
    }
    
    return ans;
  }
}