package effibenchjmh.common;

/**
 * Minimal stand-in for javafx.util.Pair (getKey/getValue API), which real LeetCode
 * submitters commonly rely on via LeetCode's judge environment but which isn't part
 * of a standard headless JRE (JavaFX was decoupled from the JDK since Java 11).
 */
public class Pair<K, V> {
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
