import java.util.*;

class Solution {

  public int fun(int i, ArrayList<Integer> list){
    int l = 0;
    int h = list.size() - 1;
    int ans = -1;
    while (l <= h) {
        int m = (l + h) / 2;
        if (list.get(m) <= i) {
            ans = m;    
            l = m + 1;   
        } else {
            h = m - 1;
        }
    }
    return ans + 1;  
}



    public int count(int l ,int h,ArrayList<Integer> list){
            return fun(h,list)-fun(l-1,list);
    }








    public long numberOfSubarrays( int[] arr ) {
        int n = arr.length;

        // Decreasing stack stores indices of elements greater than current.
        Deque<Integer> greaterStack = new ArrayDeque<>();
        // For each value, store its occurrence indices that are still valid.
        Map<Integer, Deque<Integer>> positions = new HashMap<>();

        long ans = 0;

        for (int i = 0; i < n; i++) {
            while (!greaterStack.isEmpty() && arr[greaterStack.peek()] <= arr[i]) {
                greaterStack.pop();
            }
            int prevGreater = greaterStack.isEmpty() ? -1 : greaterStack.peek();

            Deque<Integer> q = positions.get(arr[i]);
            if (q == null) {
                q = new ArrayDeque<>();
                positions.put(arr[i], q);
            }

            // Remove occurrences at or before prevGreater; they are separated
            // from i by an element larger than arr[i].
            while (!q.isEmpty() && q.peekFirst() <= prevGreater) {
                q.pollFirst();
            }

            // q.size() previous valid occurrences + the current singleton.
            ans += (long) q.size() + 1;

            q.addLast(i);
            greaterStack.push(i);
        }

        return ans;
    }

}