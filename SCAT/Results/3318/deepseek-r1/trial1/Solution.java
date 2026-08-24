class Solution {

  private static final class Key {
    final int i;
    final int j;
    final int score;

    Key(int i, int j, int score) {
      this.i = i;
      this.j = j;
      this.score = score;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Key)) return false;
      Key key = (Key) o;
      return i == key.i && j == key.j && score == key.score;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + i;
      result = 31 * result + j;
      result = 31 * result + score;
      return result;
    }
  }

public int maxOperations(int[] nums) {
    final int n = nums.length;
    Map<Key, Integer> mem = new HashMap<>();
    return Math.max(Math.max(maxOperations(nums, 0, n - 1, nums[0] + nums[1], mem),
                             maxOperations(nums, 0, n - 1, nums[n - 1] + nums[n - 2], mem)),
                    maxOperations(nums, 0, n - 1, nums[0] + nums[n - 1], mem));
  }

  // Returns the maximum number of operations that can be performed for
  // nums[i..j], s.t. all operations have the same `score`.
  private int maxOperations(int[] nums, int i, int j, int score, Map<Key, Integer> mem) {
    if (i >= j)
      return 0;
    Key key = new Key(i, j, score);
    Integer cached = mem.get(key);
    if (cached != null)
      return cached;
    final int deleteFirstTwo =
        nums[i] + nums[i + 1] == score ? 1 + maxOperations(nums, i + 2, j, score, mem) : 0;
    final int deleteLastTwo =
        nums[j] + nums[j - 1] == score ? 1 + maxOperations(nums, i, j - 2, score, mem) : 0;
    final int deleteFirstAndLast =
        nums[i] + nums[j] == score ? 1 + maxOperations(nums, i + 1, j - 1, score, mem) : 0;
    int result = Math.max(Math.max(deleteFirstTwo, deleteLastTwo), deleteFirstAndLast);
    mem.put(key, result);
    return result;
  }
    
}