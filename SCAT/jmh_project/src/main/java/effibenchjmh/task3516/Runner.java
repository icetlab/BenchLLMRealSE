package effibenchjmh.task3516;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * id=3516 | Design Neighbor Sum Service
 * Reads ONE test case (design-style call-sequence format, same as Bench.java) from
 * stdin, replays the constructor + method-call sequence on one NeighborSum
 * instance, and prints "null,<r1>,<r2>,..." (constructor has no return value)
 * to match this task's official evaluator_py.
 *
 * Reads stdin via readAllBytes() (not line-by-line) to preserve any trailing
 * empty segment -- see gen_runners.py for why readLine()-based loops break this.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        String[] top = line.split("\n", -1);
        String[] calls = top[0].split(",", -1);
        String[] callArgs = top[1].split("\\|", -1);
        for (int i = 0; i < callArgs.length; i++) callArgs[i] = callArgs[i].trim();

        NeighborSum obj = new NeighborSum(TestUtil.parseIntMatrixSemi(callArgs[0]));
        List<String> results = new ArrayList<>();
        results.add("null"); // constructor call has no return value
        for (int i = 1; i < calls.length; i++) {
            int value = Integer.parseInt(callArgs[i]);
            switch (calls[i]) {
                case "adjacentSum":
                    results.add(String.valueOf(obj.adjacentSum(value)));
                    break;
                case "diagonalSum":
                    results.add(String.valueOf(obj.diagonalSum(value)));
                    break;
                default:
                    throw new IllegalStateException("Unknown method call: " + calls[i]);
            }
        }
        System.out.println("###RESULT###");
        System.out.println(String.join(",", results));
    }
}
