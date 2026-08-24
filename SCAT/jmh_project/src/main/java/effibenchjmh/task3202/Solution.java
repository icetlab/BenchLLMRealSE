package effibenchjmh.task3202;

import java.util.*;
import java.util.stream.*;

public class Solution {
    // Function to find high-access employees based on access times.
    public List<String> findHighAccessEmployees(List<List<String>> access_times) {
        // Create a map to store access times for each employee.
        Map<String, List<Integer>> when = new HashMap<>();

        // Populate the map with access times from the input list.
        for (List<String> v : access_times) {
            String a = v.get(0), b = v.get(1);
            when.computeIfAbsent(a, k -> new ArrayList<>()).add(Integer.parseInt(b));
        }

        // List to store the names of high-access employees.
        List<String> ret = new ArrayList<>();

        // Iterate through the map to check access patterns for each employee.
        for (Map.Entry<String, List<Integer>> entry : when.entrySet()) {
            // Sort the access times for each employee.
            entry.getValue().sort(null);

            // Get the number of access times for the current employee.
            int k = entry.getValue().size();

            // Flag to indicate if the employee is a high-access employee.
            boolean flag = false;

            // Check for consecutive accesses within a 100-minute window.
            for (int i = 0; i + 3 <= k; ++i)
                flag |= entry.getValue().get(i + 2) < entry.getValue().get(i) + 100;

            // If the flag is true, the employee is considered high-access, and their name is added to the result.
            if (flag) ret.add(entry.getKey());
        }

        // Return the list containing the names of high-access employees.
        return ret;
    }
}
