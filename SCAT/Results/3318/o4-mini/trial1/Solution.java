import java.util.Arrays;

class Solution {

public int maxOperations(int[] nums) {
    int n = nums.length;
    if (n < 2) return 0;
    int ans = 0;
    // score from first two
    int score1 = nums[0] + nums[1];
    int[][] dp1 = new int[n][n];
    for (int[] row : dp1) Arrays.fill(row, -1);
    ans = Math.max(ans, maxOperations(nums, 0, n - 1, score1, dp1));
    // score from last two
    int score2 = nums[n - 1] + nums[n - 2];
    int[][] dp2 = new int[n][n];
    for (int[] row : dp2) Arrays.fill(row, -1);
    ans = Math.max(ans, maxOperations(nums, 0, n - 1, score2, dp2));
    // score from first and last
    int score3 = nums[0] + nums[n - 1];
    int[][] dp3 = new int[n][n];
    for (int[] row : dp3) Arrays.fill(row, -1);
    ans = Math.max(ans, maxOperations(nums, 0, n - 1, score3, dp3));
    return ans;
  }

  // Returns the maximum number of operations that can be performed for
  // nums[i..j], s.t. all operations have the same `score`.
  private int maxOperations(int[] nums, int i, int j, int score, int[][] dp) {
    if (i >= j) return 0;
    if (dp[i][j] != -1) return dp[i][j];
    int deleteFirstTwo = (nums[i] + nums[i + 1] == score)
        ? 1 + maxOperations(nums, i + 2, j, score, dp)
        : 0;
    int deleteLastTwo = (nums[j] + nums[j - 1] == score)
        ? 1 + maxOperations(nums, i, j - 2, score, dp)
        : 0;
    int deleteFirstAndLast = (nums[i] + nums[j] == score)
        ? 1 + maxOperations(nums, i + 1, j - 1, score, dp)
        : 0;
    dp[i][j] = Math.max(Math.max(deleteFirstTwo, deleteLastTwo), deleteFirstAndLast);
    return dp[i][j];
  }

  
    
}