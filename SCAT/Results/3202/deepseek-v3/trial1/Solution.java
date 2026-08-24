class Solution {
    public List<String> findHighAccessEmployees(List<List<String>> access_times) {
        Map<String, List<Integer>> emp_access = new HashMap<>();
        for(List<String> emp : access_times) {
            emp_access.computeIfAbsent(emp.get(0), k -> new ArrayList<>()).add(Integer.parseInt(emp.get(1)));
        }
        List<String> ans = new ArrayList<>();
        for(Map.Entry<String, List<Integer>> entry : emp_access.entrySet()) {
            List<Integer> temp = entry.getValue();
            Collections.sort(temp);
            for(int i = 2; i < temp.size(); i++) {
                if(temp.get(i) - temp.get(i - 2) < 100) {
                    ans.add(entry.getKey());
                    break;
                }
            }
        }
        return ans;
    }
}