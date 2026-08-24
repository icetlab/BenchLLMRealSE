class Solution {
    public List<String> findHighAccessEmployees(List<List<String>> access_times) {
        Map<String, List<Integer>> empAccess = new HashMap<>();
        for (List<String> access : access_times) {
            String employee = access.get(0);
            int time = Integer.parseInt(access.get(1));
            empAccess.computeIfAbsent(employee, k -> new ArrayList<>()).add(time);
        }

        List<String> highAccessEmployees = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : empAccess.entrySet()) {
            List<Integer> times = entry.getValue();
            if (times.size() < 3) {
                continue;
            }
            Collections.sort(times);
            for (int i = 2; i < times.size(); i++) {
                if (times.get(i) - times.get(i - 2) < 100) {
                    highAccessEmployees.add(entry.getKey());
                    break;
                }
            }
        }
        return highAccessEmployees;
    }
}