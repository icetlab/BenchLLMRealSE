class Solution {
    public int maxGoodNumber(int[] nums) {
        // compute bit-lengths
        int[] len = new int[3];
        for (int i = 0; i < 3; i++) {
            len[i] = nums[i] == 0 ? 1 : 32 - Integer.numberOfLeadingZeros(nums[i]);
        }
        // build six concatenations via bit shifts and pick max
        int max = (nums[0] << (len[1] + len[2])) | (nums[1] << len[2]) | nums[2];
        int v;
        v = (nums[0] << (len[2] + len[1])) | (nums[2] << len[1]) | nums[1]; if (v > max) max = v;
        v = (nums[1] << (len[0] + len[2])) | (nums[0] << len[2]) | nums[2]; if (v > max) max = v;
        v = (nums[1] << (len[2] + len[0])) | (nums[2] << len[0]) | nums[0]; if (v > max) max = v;
        v = (nums[2] << (len[0] + len[1])) | (nums[0] << len[1]) | nums[1]; if (v > max) max = v;
        v = (nums[2] << (len[1] + len[0])) | (nums[1] << len[0]) | nums[0]; if (v > max) max = v;
        return max;
    }
}