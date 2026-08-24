class Solution {
    public char kthCharacter(long k, int[] operations) {
        
        int a = findit(k,operations);

        return (char)('a' + a%26);

        // return 

        // return 'a';
    }

    private int findit(long k, int[] operations){
        int ans = 0;
        long m = k - 1;
        while (m > 0) {
            long bit = Long.highestOneBit(m);
            int x = Long.numberOfTrailingZeros(bit);
            if (operations[x] == 1) {
                ans++;
            }
            m -= bit;
        }
        return ans;
       
    }


}