class Solution {
    public boolean hasSameDigits(String s) {
        int n = s.length();
        if (n < 2) {
            return false;
        }

        int[] digits = new int[n];
        for (int i = 0; i < n; i++) {
            digits[i] = s.charAt(i) - '0';
        }

        while (digits.length > 2) {
            int[] nextDigits = new int[digits.length - 1];
            for (int i = 0; i < digits.length - 1; i++) {
                nextDigits[i] = (digits[i] + digits[i + 1]) % 10;
            }
            digits = nextDigits;
        }

        return digits[0] == digits[1];
        
    }
}