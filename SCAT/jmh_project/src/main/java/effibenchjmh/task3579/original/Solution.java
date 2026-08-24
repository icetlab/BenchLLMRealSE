package effibenchjmh.task3579.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int maxGoodNumber(int[] nums) {
        String num1 = Integer.toBinaryString(nums[0]);
        String num2 = Integer.toBinaryString(nums[1]);
        String num3 = Integer.toBinaryString(nums[2]);
        while(num1.charAt(0)=='0'){
            num1.substring(1);
        }
        while(num2.charAt(0)=='0'){
            num2.substring(1);
        }
        while(num3.charAt(0)=='0'){
            num3.substring(1);
        }
        ArrayList<String> ans = new ArrayList<String>();
        ans.add(num1+num2+num3);
        ans.add(num1+num3+num2);
        ans.add(num2+num1+num3);
        ans.add(num2+num3+num1);
        ans.add(num3+num1+num2);
        ans.add(num3+num2+num1);
        ans.sort((s1, s2) -> s2.compareTo(s1));
        return Integer.parseInt(ans.get(0), 2);    
    }
}
