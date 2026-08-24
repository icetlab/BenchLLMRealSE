package effibenchjmh.task3355;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumLevels(int[] possible) {
        int val=0;
        for(int i:possible)
        {
            val += i == 1 ? 1 : -1;
        }
        if(val<0)
        {
            if(possible.length==2)
            {
                return -1;
            }
            else if(Math.abs(val) == possible.length)
            {
                return 1;
            }
        }
        int first=0;
        for(int i=0;i<possible.length;i++)
        {
            if(possible[i] == 1)
            {
                first++;
                val--;
            }
            else
            {
                first--;
                val++;
            }
            if(first>val)
            {
                if(i == possible.length-1)
                {
                    return -1;
                }
                return i+1;
            }
        }
        return -1;
    }
}
