class Solution {
    public int beautifulSubstrings(String s, int k) {
        int n = s.length();
        int count = 0;
        boolean[] isVowel = new boolean[26];
        String vowels = "aeiou";
        for (int i = 0; i < vowels.length(); i++) {
            isVowel[vowels.charAt(i) - 'a'] = true;
        }
        
        // Precompute prefix sums: prefixVowels[i] = vowel count in s[0..i-1]
        int[] prefixVowels = new int[n + 1];
        int[] prefixConsonants = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixVowels[i + 1] = prefixVowels[i] + (isVowel[s.charAt(i) - 'a'] ? 1 : 0);
            prefixConsonants[i + 1] = prefixConsonants[i] + (isVowel[s.charAt(i) - 'a'] ? 0 : 1);
        }
        
        // For each pair (i, j), check if substring s[i..j] is beautiful
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int vowelCount = prefixVowels[j + 1] - prefixVowels[i];
                int consonantCount = prefixConsonants[j + 1] - prefixConsonants[i];
                if (vowelCount == consonantCount && (vowelCount * consonantCount) % k == 0) {
                    count++;
                }
            }
        }
        return count;
    }
}