package effibenchjmh.task3202.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public List<String> findHighAccessEmployees(List<List<String>> access_times) {
        Map<String, List<String>> emp_access = new HashMap<>();
        for(List<String> emp : access_times) {
            List<String> temp = emp_access.containsKey(emp.get(0)) ? emp_access.get(emp.get(0)) : new ArrayList<String>();
            temp.add(emp.get(1));
            emp_access.put(emp.get(0), temp);
        }
        List<String> ans = new ArrayList<>();
        for(Map.Entry<String, List<String>> entry : emp_access.entrySet()) {
            List<String> temp = entry.getValue();
            Collections.sort(temp);
            System.out.println(temp);
            for(int i = 2; i < temp.size(); i++) {
                if(Integer.parseInt(temp.get(i)) - Integer.parseInt(temp.get(i - 2)) < 100) {
                    ans.add(entry.getKey());
                    break;
                }
            }
        }
        return ans;
    }
}
