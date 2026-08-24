package effibenchjmh.task3553.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean checkTwoChessboards(String coordinate1, String coordinate2) {
        return ((coordinate1.charAt(0)-'a') + (coordinate1.charAt(1)-'1')) % 2 == ((coordinate2.charAt(0)-'a') + (coordinate2.charAt(1)-'1')) % 2;

    }
}
