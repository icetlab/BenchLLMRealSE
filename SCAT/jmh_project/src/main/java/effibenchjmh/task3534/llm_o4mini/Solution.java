package effibenchjmh.task3534.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countPairs(int[] nums) {
        int cnt=0;
        int n = nums.length;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                if(f(nums[i],nums[j])){
                    cnt++;
                }
            }
        }
        return cnt;
    }
    public boolean f(int a,int b){
        String x = Integer.toString(a);
        String y = Integer.toString(b);
        if(x.equals(y)) return true;
        if(same(x,y)){
            return true;
        }
        if(same(y,x)){
            return true;
        }
        return false;
    }

    public boolean same(String a, String b){
        int bVal = Integer.parseInt(b);
        for(int i=0;i<a.length();i++){
            for(int j=i+1;j<a.length();j++){
                char arr[] = a.toCharArray();
                swap(arr,i,j);
                String curr = new String(arr);
                if (Integer.parseInt(curr) == bVal) return true;
                swap(arr,i,j);
            }
        }
        return false;
    }

    void swap(char arr[],int i,int j){
        char temp=  arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }
}
