package effibenchjmh.task3611.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public static int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];
        for(int j=0; j<n; j++){
            String s = Integer.toBinaryString(nums.get(j));

            boolean AllsetBit = true;
            for(char ch : s.toCharArray()){
                if (ch == '0'){
                    AllsetBit = false;
                    break;
                }
            }
            
            if (nums.get(j) == 1 || nums.get(j) == 2){
                ans[j] = -1;
                continue;
            }


            boolean first = false;
            boolean second = false;
            StringBuilder str = new StringBuilder();

            for(int i=0; i<s.length() && !AllsetBit; i++){
                if (s.charAt(i) == '1'){
                    if (!first){
                        first = true;
                        str.append("1");
                    }else{
                        if (!second){
                            boolean flag = true;
                            for(int k=i+1; k<s.length(); k++){
                                if (s.charAt(k) == '0'){
                                    flag = false;
                                    break;
                                }
                            }
                            
                            if (flag){
                                second = true;
                                str.append("0");  
                            }else{
                                str.append("1");
                            }
                            
                            
                        }else{
                            str.append("1");
                        }
                    }
                }
                else str.append("0");
            }

            if (AllsetBit){
                str = new StringBuilder(s);
                str.setCharAt(0, '0');
            }

            int num = Integer.parseInt(str.toString(), 2);
            ans[j] = num;
        }

        return ans;
    }
}
