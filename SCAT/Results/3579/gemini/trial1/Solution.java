class Solution {
    public int maxGoodNumber(int[] nums) {
        String s1 = Integer.toBinaryString(nums[0]);
        String s2 = Integer.toBinaryString(nums[1]);
        String s3 = Integer.toBinaryString(nums[2]);

        String maxStr = s1 + s2 + s3;

        String current = s1 + s3 + s2;
        if (current.compareTo(maxStr) > 0) maxStr = current;

        current = s2 + s1 + s3;
        if (current.compareTo(maxStr) > 0) maxStr = current;

        current = s2 + s3 + s1;
        if (current.compareTo(maxStr) > 0) maxStr = current;

        current = s3 + s1 + s2;
        if (current.compareTo(maxStr) > 0) maxStr = current;

        current = s3 + s2 + s1;
        if (current.compareTo(maxStr) > 0) maxStr = current;

        return Integer.parseInt(maxStr, 2);    
    }
}