class Solution {
    public int countKConstraintSubstrings(String s, int k) {
        int n = s.length();
        int needZero = k + 1;
        int needOne = k + 1;
        long total = (long) n * (n + 1) / 2;
        long bad = 0;
        int zeros = 0;
        int ones = 0;
        int r = 0;

        for (int l = 0; l < n; l++) {
            while (r < n && !(zeros >= needZero && ones >= needOne)) {
                if (s.charAt(r) == '0') {
                    zeros++;
                } else {
                    ones++;
                }
                r++;
            }

            if (zeros >= needZero && ones >= needOne) {
                bad += n - r + 1;
            }

            if (s.charAt(l) == '0') {
                zeros--;
            } else {
                ones--;
            }
        }

        return (int) (total - bad);
    }
    public boolean cot(String s,int k){
        int z = 0;
        int o = 0;
        for(char c : s.toCharArray()){
            if (c == '0'){
                z++;
            }
            else{
                o++;
            }
        }
        if (o > k && z > k){
            return false;
        } 
        return true;
    }
}