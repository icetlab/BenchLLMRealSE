import java.util.Arrays;

class Solution {
    public String triangleType(int[] nums) {
        Arrays.sort(nums);
        
        // Check if triangle is possible
        if (nums[0] + nums[1] <= nums[2]) {
            return "none";
        }
        
        // Check for equilateral (all sides equal)
        if (nums[0] == nums[1] && nums[1] == nums[2]) {
            return "equilateral";
        }
        
        // Check for isosceles (at least two sides equal)
        if (nums[0] == nums[1] || nums[1] == nums[2]) {
            return "isosceles";
        }
        
        // Otherwise it's scalene
        return "scalene";
    }
}