package effibenchjmh.task3361.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public String findLatestTime(String s) {
        char[] time = s.toCharArray();

        // Handle hours (00-11)
        if (time[0] == '?') {
            if (time[1] == '?' || time[1] <= '1') {
                time[0] = '1';
            } else {
                time[0] = '0';
            }
        }

        if (time[1] == '?') {
            if (time[0] == '1') {
                time[1] = '1';
            } else { // time[0] is '0'
                time[1] = '9';
            }
        }

        // Handle minutes (00-59)
        if (time[3] == '?') {
            time[3] = '5';
        }

        if (time[4] == '?') {
            time[4] = '9';
        }

        return new String(time);
    }
}
