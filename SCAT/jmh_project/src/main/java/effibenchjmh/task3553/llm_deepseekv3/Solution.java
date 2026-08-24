package effibenchjmh.task3553.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean checkTwoChessboards(String coordinate1, String coordinate2) {
        int col1 = coordinate1.charAt(0) - 'a';
        int row1 = coordinate1.charAt(1) - '1';
        int col2 = coordinate2.charAt(0) - 'a';
        int row2 = coordinate2.charAt(1) - '1';
        return ((col1 + row1) % 2) == ((col2 + row2) % 2);

    }
}
