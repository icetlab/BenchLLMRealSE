class Solution {
   public int maxAdjacentDistance(int[] nums) {
		 int max = 0;
		 for(int i=0;i<nums.length;i++) {
			 int j = (i + 1) % nums.length;
			 int r = Math.abs(nums[i]-nums[j]);
			 if (r > max) max = r;
		 }
		 return max;
	    }
}