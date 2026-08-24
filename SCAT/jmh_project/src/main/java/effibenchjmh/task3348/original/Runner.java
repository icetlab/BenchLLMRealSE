package effibenchjmh.task3348.original;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3348 | Minimum Cost Walk in Weighted Graph
 * Reads ONE test case (multi-section stdin format, same as Bench.java) from
 * stdin, runs it through Solution.minimumCost(), and prints the int[] result
 * newline-separated to match this task's official evaluator_py.
 *
 * Reads stdin via readAllBytes() (not line-by-line) to preserve any trailing
 * empty segment -- see gen_runners.py for why readLine()-based loops break this.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        String[] rows = line.split("\n", -1);
        int idx = 0;
        int n = Integer.parseInt(rows[idx++].trim());
        int numEdges = Integer.parseInt(rows[idx++].trim());
        int[][] edges = new int[numEdges][];
        for (int i = 0; i < numEdges; i++) edges[i] = TestUtil.parseIntArraySpaceSep(rows[idx++]);
        int numQueries = Integer.parseInt(rows[idx++].trim());
        int[][] query = new int[numQueries][];
        for (int i = 0; i < numQueries; i++) query[i] = TestUtil.parseIntArraySpaceSep(rows[idx++]);

        Solution sol = new Solution();
        int[] result = sol.minimumCost(n, edges, query);
        System.out.println("###RESULT###");
        System.out.println(java.util.stream.IntStream.of(result).mapToObj(String::valueOf).collect(java.util.stream.Collectors.joining("\n")));
    }
}
