package effibenchjmh.task3684.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean hasMatch(String str, String subStr) {
        int starIdx = subStr.indexOf('*');
        String prefix = subStr.substring(0, starIdx);
        String suffix = subStr.substring(starIdx + 1);
        int prefixPos = str.indexOf(prefix);
        if (prefixPos == -1) return false;
        int suffixStart = prefixPos + prefix.length();
        return str.indexOf(suffix, suffixStart) != -1;
    }
}
