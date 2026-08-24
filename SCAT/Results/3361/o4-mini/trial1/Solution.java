class Solution {
    public String findLatestTime(String s) {
        char[] t = s.toCharArray();
        // hours tens
        if (t[0] == '?') {
            if (t[1] == '?' || t[1] <= '1') {
                t[0] = '1';
            } else {
                t[0] = '0';
            }
        }
        // hours ones
        if (t[1] == '?') {
            t[1] = (t[0] == '0') ? '9' : '1';
        }
        // minute tens
        if (t[3] == '?') {
            t[3] = '5';
        }
        // minute ones
        if (t[4] == '?') {
            t[4] = '9';
        }
        return new String(t);
    }
}