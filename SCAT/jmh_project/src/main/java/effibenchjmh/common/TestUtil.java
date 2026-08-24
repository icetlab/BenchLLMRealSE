package effibenchjmh.common;

import java.util.ArrayList;
import java.util.List;

/** Shared parsing helpers for EffiBench-X generated_tests input strings.
 *  Each task's test-case generator invented its own ad hoc delimiter
 *  convention, so these are building blocks, not a single universal parser. */
public final class TestUtil {

    private TestUtil() {}

    public static int[] parseIntArray(String s) {
        if (s.trim().isEmpty()) return new int[0];
        String[] parts = s.split(",", -1);
        int[] out = new int[parts.length];
        for (int i = 0; i < parts.length; i++) out[i] = Integer.parseInt(parts[i].trim());
        return out;
    }

    public static List<Integer> parseIntList(String s) {
        List<Integer> out = new ArrayList<>();
        for (int v : parseIntArray(s)) out.add(v);
        return out;
    }

    public static int[][] parseIntMatrixSemi(String s) {
        if (s.trim().isEmpty()) return new int[0][];
        String[] rows = s.split(";", -1);
        int[][] out = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) out[i] = parseIntArray(rows[i]);
        return out;
    }

    public static List<List<Integer>> parseIntListMatrix(String s) {
        List<List<Integer>> out = new ArrayList<>();
        if (s.trim().isEmpty()) return out;
        String[] rows = s.split(";", -1);
        for (String row : rows) out.add(parseIntList(row));
        return out;
    }

    public static char[][] parseCharMatrixSemi(String s) {
        if (s.trim().isEmpty()) return new char[0][];
        String[] rows = s.split(";", -1);
        char[][] out = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] cells = rows[i].split(",", -1);
            out[i] = new char[cells.length];
            for (int j = 0; j < cells.length; j++) out[i][j] = cells[j].trim().charAt(0);
        }
        return out;
    }

    /** Format: "{rows} {cols}\n{row0 comma-separated}\n{row1}\n..." (id=3677 style). */
    public static int[][] parseIntMatrixDimHeader(String s) {
        String[] lines = s.split("\n", -1);
        String[] header = lines[0].split(" ", -1);
        int rows = Integer.parseInt(header[0].trim());
        int[][] out = new int[rows][];
        for (int i = 0; i < rows; i++) out[i] = parseIntArray(lines[i + 1]);
        return out;
    }

    /** Format: "{n} {row0;row1;...}" -- leading redundant count, then semicolon/comma matrix (id=3648 style). */
    public static int[][] parseIntMatrixCountPrefix(String s) {
        int firstSpace = s.indexOf(' ');
        String rest = s.substring(firstSpace + 1);
        return parseIntMatrixSemi(rest);
    }

    public static String[] parseStringArray(String s) {
        if (s.trim().isEmpty()) return new String[0];
        String[] parts = s.split(",", -1);
        for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
        return parts;
    }

    public static List<List<String>> parseStringListMatrix(String s) {
        List<List<String>> out = new ArrayList<>();
        if (s.trim().isEmpty()) return out;
        String[] rows = s.split(";", -1);
        for (String row : rows) {
            List<String> inner = new ArrayList<>();
            for (String cell : row.split(",", -1)) inner.add(cell.trim());
            out.add(inner);
        }
        return out;
    }

    /** Format: "{rows}\n{row0 comma-separated}\n{row1}\n..." -- header is just the row count (id=3519/3737 style). */
    public static int[][] parseIntMatrixRowCountHeader(String s) {
        String[] lines = s.split("\n", -1);
        int rows = Integer.parseInt(lines[0].trim());
        int[][] out = new int[rows][];
        for (int i = 0; i < rows; i++) out[i] = parseIntArray(lines[i + 1]);
        return out;
    }

    public static int[] parseIntArraySpaceSep(String s) {
        if (s.trim().isEmpty()) return new int[0];
        String[] parts = s.trim().split("\\s+", -1);
        int[] out = new int[parts.length];
        for (int i = 0; i < parts.length; i++) out[i] = Integer.parseInt(parts[i].trim());
        return out;
    }
}
