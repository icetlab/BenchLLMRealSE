package effibenchjmh.task3684.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean hasMatch(String str, String subStr) {
        int starIndex = subStr.indexOf('*');
        String prefix = subStr.substring(0, starIndex);
        String suffix = subStr.substring(starIndex + 1);

        // Find the first occurrence of the prefix.
        int prefixPos = str.indexOf(prefix);
        if (prefixPos == -1) {
            return false;
        }

        // Define where to start searching for the suffix: right after the prefix.
        int searchStartForSuffix = prefixPos + prefix.length();

        // Search for the suffix in the remainder of the string without creating a new substring.
        return str.indexOf(suffix, searchStartForSuffix) != -1;
    }
}
