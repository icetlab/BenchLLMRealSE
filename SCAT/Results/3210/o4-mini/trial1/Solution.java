import java.util.*;

class Solution {
    public int beautifulSubstrings(String s, int k) {
        int n = s.length();
        // factor k to compute minimal M so that M^2 % k == 0
        int M = 1;
        int temp = k;
        for (int p = 2; p * p <= temp; p++) {
            if (temp % p == 0) {
                int cnt = 0;
                while (temp % p == 0) { temp /= p; cnt++; }
                int exp = (cnt + 1) / 2;
                int mul = 1;
                for (int i = 0; i < exp; i++) mul *= p;
                M *= mul;
            }
        }
        if (temp > 1) {
            M *= temp;
        }
        long T = 2L * M;
        Map<Long,Integer> map = new HashMap<>();
        long diff = 0, ans = 0;
        // initial prefix at i=0 with r=0
        long key0 = (diff << 32) | 0L;
        map.put(key0, 1);
        String vowels = "aeiou";
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            diff += (vowels.indexOf(c) >= 0 ? 1 : -1);
            long r = (i + 1) % T;
            long key = (diff << 32) | r;
            int cur = map.getOrDefault(key, 0);
            ans += cur;
            map.put(key, cur + 1);
        }
        return (int) ans;
    }   
}