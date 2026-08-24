package effibenchjmh.task3553.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean checkTwoChessboards(String coordinate1, String coordinate2) {
        // A square's color is determined by the parity of the sum of its coordinates.
        // The parity of (col_char + row_char) is the same as (col_index + row_index).
        // We use bitwise AND (& 1) which is generally faster than modulo 2 for parity checks.
        return ((coordinate1.charAt(0) + coordinate1.charAt(1)) & 1) ==
               ((coordinate2.charAt(0) + coordinate2.charAt(1)) & 1);

    }
}
