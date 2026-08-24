import java.util.*;

class Solution {

    private List<List<Integer>> rowsForValue;
    private int[] rowMatch;
    private boolean[] seenRow;

    private boolean canMatch(int valueIdx) {
        for (int row : rowsForValue.get(valueIdx)) {
            if (seenRow[row]) {
                continue;
            }
            seenRow[row] = true;
            if (rowMatch[row] == -1 || canMatch(rowMatch[row])) {
                rowMatch[row] = valueIdx;
                return true;
            }
        }
        return false;
    }

    public int maxScore(List<List<Integer>> grid) {
        Map<Integer, Set<Integer>> rowsByValue = new HashMap<>();
        for (int r = 0; r < grid.size(); r++) {
            List<Integer> row = grid.get(r);
            for (int value : row) {
                Set<Integer> rows = rowsByValue.get(value);
                if (rows == null) {
                    rows = new HashSet<>();
                    rowsByValue.put(value, rows);
                }
                rows.add(r);
            }
        }

        List<Integer> values = new ArrayList<>(rowsByValue.keySet());
        Collections.sort(values, Collections.reverseOrder());

        rowsForValue = new ArrayList<>(values.size());
        for (int value : values) {
            rowsForValue.add(new ArrayList<>(rowsByValue.get(value)));
        }

        rowMatch = new int[grid.size()];
        Arrays.fill(rowMatch, -1);
        seenRow = new boolean[grid.size()];

        int score = 0;
        for (int i = 0; i < values.size(); i++) {
            Arrays.fill(seenRow, false);
            if (canMatch(i)) {
                score += values.get(i);
            }
        }
        return score;
    }
}