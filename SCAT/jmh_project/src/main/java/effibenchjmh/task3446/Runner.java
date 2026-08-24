package effibenchjmh.task3446;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3446 | Find the Number of Good Pairs I
 * Reads ONE test case's raw input from stdin, runs it through Solution.numberOfPairs(),
 * and prints the result serialized to match this task's official evaluator_py.
 * Used for correctness verification (not performance measurement -- see Bench.java
 * in this package for the JMH benchmark).
 *
 * NOTE: reads stdin via readAllBytes(), NOT line-by-line via BufferedReader.readLine().
 * readLine()-based loops silently drop a trailing empty line before EOF (there is no
 * way to distinguish "no more input" from "one final empty line" that way), which
 * breaks tasks whose format encodes an empty trailing segment (e.g. an empty array
 * parameter). readAllBytes() preserves the exact input bytes, matching how the JMH
 * benchmark reads its resource case files.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split("\\|", -1);
        var result = sol.numberOfPairs(TestUtil.parseIntArray(tok[0]), TestUtil.parseIntArray(tok[1]), Integer.parseInt(tok[2]));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
