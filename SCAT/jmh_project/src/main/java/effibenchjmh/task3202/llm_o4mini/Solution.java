package effibenchjmh.task3202.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public List<String> findHighAccessEmployees(List<List<String>> access_times) {
        Map<String, List<Integer>> emp_access = new java.util.LinkedHashMap<>();
        for (List<String> emp : access_times) {
    int time = Integer.parseInt(emp.get(1));
    List<Integer> temp = emp_access.computeIfAbsent(emp.get(0), k -> new ArrayList<>());
    temp.add(time);
}
        List<String> ans = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : emp_access.entrySet()) {
            List<Integer> temp = entry.getValue();
            Collections.sort(temp);
            for(int i = 2; i < temp.size(); i++) {
                if (temp.get(i) - temp.get(i - 2) < 100) {
                    ans.add(entry.getKey());
                    break;
                }
            }
        }
        return ans;
    }
}
