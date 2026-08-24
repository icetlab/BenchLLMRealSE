class Solution {
    public int earliestSecondToMarkIndices(int[] nums, int[] changeIndices) {
        if (nums.length == 1 && nums[0] == 0) {
            return 1;
        }

        changeIndices = Arrays.stream(changeIndices).map(x -> x - 1).toArray();

        long total = 0;
        for (int i = 0; i < nums.length; i++) {
            total += nums[i];
        }
        total += nums.length;

        int low = 0, high = changeIndices.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (isPossible(nums, changeIndices, mid, total)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return isPossible(nums, changeIndices, low, total) ? low + 1 : -1;
    }

    private boolean isPossible(int[] nums, int[] changeIndices, int mid, long total) {
        if (mid >= changeIndices.length) {
            return false;
        }

        int[] firstIndices = new int[nums.length];
        Arrays.fill(firstIndices, -1);
        for (int i = 0; i <= mid; i++) {
            int idx = changeIndices[i];
            if (nums[idx] > 0 && firstIndices[idx] == -1) {
                firstIndices[idx] = i;
            }
        }

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        int available = 0;
        for (int i = mid; i >= 0; i--) {
            int idx = changeIndices[i];
            if (firstIndices[idx] != -1 && firstIndices[idx] == i) {
                int cost = nums[idx];
                if (available > 0) {
                    priorityQueue.offer(cost);
                    available--;
                } else if (!priorityQueue.isEmpty() && priorityQueue.peek() < cost) {
                    priorityQueue.poll();
                    priorityQueue.offer(cost);
                    available++;
                } else {
                    available++;
                }
            } else {
                available++;
            }
        }

        while (!priorityQueue.isEmpty()) {
            total -= (priorityQueue.poll() + 1);
        }

        return total <= available;
    }
}