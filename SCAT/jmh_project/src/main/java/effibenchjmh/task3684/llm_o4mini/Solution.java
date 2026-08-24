package effibenchjmh.task3684.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean hasMatch(String str, String subStr) {
         int startIndex = subStr.indexOf("*");
         String prefix = subStr.substring(0,startIndex);
         String suffix = subStr.substring(startIndex+1);
         int idx = str.indexOf(prefix);
         if (idx < 0) return false;
         int from = idx + prefix.length();
         if (suffix.isEmpty()) return true;
         return str.indexOf(suffix, from) >= 0;
    }
}
