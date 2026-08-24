class Solution {
       public int maximumStrongPairXor(int[] A) {
        int res = 0;
        for (int i = 20; i >= 0; i--) {
            res <<= 1;
            HashMap<Integer, Integer> pref = new HashMap<>(), pref2 = new HashMap<>();
            for (int a : A) {
                int p = a >> i;
                pref.merge(p, a, Integer::min);
                pref2.merge(p, a, Integer::max);
            }
            for (java.util.Map.Entry<Integer, Integer> entry : pref.entrySet()) {
                int x = entry.getKey();
                int min_x = entry.getValue();
                int y = res ^ 1 ^ x;
                if (x >= y) {
                    Integer max_y = pref2.get(y);
                    if (max_y != null && min_x <= max_y * 2) {
                        res |= 1;
                        break;
                    }
                }
            }
        }
        return res;
    }
}