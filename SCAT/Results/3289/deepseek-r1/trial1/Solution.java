class Solution {
    public int earliestSecondToMarkIndices(int[] nums, int[] changeIndices) {
        if (nums.length == 1 && nums[0] == 0) {
            return 1;
        }

        int[] converted = new int[changeIndices.length];
        for (int i = 0; i < changeIndices.length; i++) {
            converted[i] = changeIndices[i] - 1;
        }
        changeIndices = converted;

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

        int n = nums.length;
        int[] first = new int[n];
        for (int i = 0; i < n; i++) {
            first[i] = -1;
        }
        for (int i = 0; i <= mid; i++) {
            int idx = changeIndices[i];
            if (nums[idx] > 0 && first[idx] == -1) {
                first[idx] = i;
            }
        }

        int[] heap = new int[n];
        int heapSize = 0;
        int available = 0;

        for (int i = mid; i >= 0; i--) {
            int idx = changeIndices[i];
            if (first[idx] == i) {
                int v = nums[idx];
                if (available > 0) {
                    heap[heapSize] = v;
                    int cur = heapSize;
                    heapSize++;
                    while (cur > 0) {
                        int parent = (cur - 1) >>> 1;
                        if (heap[parent] <= heap[cur]) {
                            break;
                        }
                        int tmp = heap[parent];
                        heap[parent] = heap[cur];
                        heap[cur] = tmp;
                        cur = parent;
                    }
                    available--;
                } else if (heapSize > 0 && heap[0] < v) {
                    heap[0] = v;
                    int cur = 0;
                    while (true) {
                        int left = (cur << 1) + 1;
                        int right = left + 1;
                        int smallest = cur;
                        if (left < heapSize && heap[left] < heap[smallest]) {
                            smallest = left;
                        }
                        if (right < heapSize && heap[right] < heap[smallest]) {
                            smallest = right;
                        }
                        if (smallest == cur) {
                            break;
                        }
                        int tmp = heap[smallest];
                        heap[smallest] = heap[cur];
                        heap[cur] = tmp;
                        cur = smallest;
                    }
                    available++;
                } else {
                    available++;
                }
            } else {
                available++;
            }
        }

        for (int i = 0; i < heapSize; i++) {
            total -= (heap[i] + 1);
        }
        return total <= available;
    }
}