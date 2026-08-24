package effibenchjmh.task3553.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean checkTwoChessboards(String coordinate1, String coordinate2) {
        return ((coordinate1.charAt(0) + coordinate1.charAt(1)) & 1)
            == ((coordinate2.charAt(0) + coordinate2.charAt(1)) & 1);
    }
}
