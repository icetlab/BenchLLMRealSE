class Solution {
    public boolean isPossibleToRearrange(String s, String t, int k) {
        if(s.length()%k!=0 || s.length()!=t.length()){
            return false;
        }

        String[] s1 = new String[k];
        String[] t1 = new String[k];
        int sidx = 0;   
        int tidx =0 ;
        for(int i=0; i<k; i++){
            StringBuilder sb = new StringBuilder("");
            StringBuilder sb2 = new StringBuilder("");

            for(int j=0; j<s.length()/k; j++){
                sb.append(s.charAt(sidx++));
                sb2.append(t.charAt(tidx++));
            }
            s1[i]=  sb.toString();
            t1[i]=  sb2.toString();
        }

        java.util.Map<String, Integer> counts = new java.util.HashMap<String, Integer>();
        for (String part : s1) {
            counts.put(part, counts.getOrDefault(part, 0) + 1);
        }
        for (String part : t1) {
            int count = counts.getOrDefault(part, 0);
            if (count == 0) {
                return false;
            }
            if (count == 1) {
                counts.remove(part);
            } else {
                counts.put(part, count - 1);
            }
        }
        return counts.isEmpty();
    }
}