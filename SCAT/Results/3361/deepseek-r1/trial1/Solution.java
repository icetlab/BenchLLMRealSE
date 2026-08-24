class Solution {
    public String findLatestTime(String s) {
        char[] ch = s.toCharArray();
        // hour part (indices 0,1)
        if (ch[0] == '?' && ch[1] == '?') {
            ch[0] = '1';
            ch[1] = '1';
        } else if (ch[0] == '?') {
            if (ch[1] <= '1') {
                ch[0] = '1';
            } else {
                ch[0] = '0';
            }
        } else if (ch[0] == '1') {
            if (ch[1] == '?') {
                ch[1] = '1';
            }
        } else if (ch[0] == '0') {
            if (ch[1] == '?') {
                ch[1] = '9';
            }
        }
        // minute part (indices 3,4; index 2 is ':')
        if (ch[3] == '?' && ch[4] == '?') {
            ch[3] = '5';
            ch[4] = '9';
        } else if (ch[3] == '?') {
            ch[3] = '5';
        } else if (ch[3] == '5') {
            if (ch[4] == '?') {
                ch[4] = '9';
            }
        } else {
            // ch[3] is '0'..'4'
            if (ch[4] == '?') {
                ch[4] = '9';
            }
        }
        return new String(ch);
    }
}