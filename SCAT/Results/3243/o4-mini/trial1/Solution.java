class Solution {
    public long numberOfPowerfulInt(long start, long finish, int limit, String s) {
        long initStart = start;
        long result = 0;
        long x = Long.parseLong(s);
        int nOfSuffixDigits = s.length();
        long rang = 1;
        for (int i = 0; i < nOfSuffixDigits; i++) rang *= 10;

        // get proper start
        if (x >= start) {
            start = x;
        } else {           
            start = start % rang <= x ? start/rang * rang + x : start/rang * rang + x + rang;
        }

        String res = getResForStart(start, limit, rang);
        start = Long.valueOf(res + s);

        // get proper finish
        if (x > finish) {
            return 0;
        } else {
            res = getResForFinish(finish, limit, rang);
            long currFinish = Long.valueOf(res) * rang;
            if (finish % rang >= x) {
                finish = currFinish + x;
            } else {
                finish = currFinish + x < finish ? currFinish + x : currFinish + x - rang;  
            }
        }

        res = getResForFinish(finish, limit, rang);
        finish = Long.valueOf(res + s);

        if (start > finish) return 0;
        if (start == finish && x == start) return 1;

        int shift = initStart > x ? 1 : 0;

        return getCalc(finish, limit, rang) - getCalc(start, limit, rang) + shift;
    }

    private long getCalc(long finish, int limit, long rang) {
        long m = finish / rang;
        if (m == 0) return 0;
        long sum = (m % 10) + 1;
        long register = limit + 1;
        m /= 10;
        while (m > 0) {
            int digit = (int)(m % 10);
            sum += digit * register;
            register *= (limit + 1);
            m /= 10;
        }
        return sum;
    }

    private String getResForFinish(long finish, int limit, long rang) {
        String str = String.valueOf(finish / rang);
        StringBuilder sb = new StringBuilder(str.length());
        boolean capped = false;
        for (int i = 0; i < str.length(); i++) {
            int d = str.charAt(i) - '0';
            if (!capped && d > limit) capped = true;
            sb.append(capped ? limit : d);
        }
        return sb.toString();
    }

    private String getResForStart(long start, int limit, long rang) {
        char[] chars = String.valueOf(start / rang).toCharArray();
        int n = chars.length;
        // find first digit > limit
        int pos = -1;
        for (int i = 0; i < n; i++) {
            if (chars[i] - '0' > limit) { pos = i; break; }
        }
        if (pos < 0) return new String(chars);
        // carry back to adjust
        int j = pos - 1;
        while (j >= 0 && chars[j] - '0' == limit) j--;
        if (j < 0) {
            // overflow: produce 1 followed by zeros
            StringBuilder sb = new StringBuilder(n + 1).append('1');
            for (int k = 0; k < n; k++) sb.append('0');
            return sb.toString();
        }
        chars[j] = (char)(chars[j] + 1);
        for (int k = j + 1; k < n; k++) chars[k] = '0';
        return new String(chars);
    }
}
