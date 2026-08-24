package effibenchjmh.task3202.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public List<String> findHighAccessEmployees(List<List<String>> access_times) {
        Map<String, List<Integer>> empTimes = new HashMap<>();
        for (List<String> record : access_times) {
            String name = record.get(0);
            int time = Integer.parseInt(record.get(1));
            empTimes.computeIfAbsent(name, k -> new ArrayList<>()).add(time);
        }
        List<String> ans = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : empTimes.entrySet()) {
            List<Integer> times = entry.getValue();
            Collections.sort(times);
            for (int i = 2; i < times.size(); i++) {
                if (times.get(i) - times.get(i - 2) < 100) {
                    ans.add(entry.getKey());
                    break;
                }
            }
        }
        return ans;
    }
}
