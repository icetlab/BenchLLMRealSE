class Solution {
        public int countOfSubstrings(String word, int k) {
        int n = word.length();
        int count = 0;

        for (int i = 0; i < n; i++) {
            int consonants = 0;
            int[] vowelCounts = new int[5];
            int distinctVowels = 0;

            for (int j = i; j < n; j++) {
                char c = word.charAt(j);
                
                int vowelIndex = getVowelIndex(c);
                if (vowelIndex != -1) { // It's a vowel
                    if (vowelCounts[vowelIndex] == 0) {
                        distinctVowels++;
                    }
                    vowelCounts[vowelIndex]++;
                } else { // It's a consonant
                    consonants++;
                }

                if (consonants == k && distinctVowels == 5) {
                    count++;
                }
                
                if (consonants > k) {
                    break;
                }
            }
        }
        return count;
    }

    private int getVowelIndex(char c) {
        switch (c) {
            case 'a': return 0;
            case 'e': return 1;
            case 'i': return 2;
            case 'o': return 3;
            case 'u': return 4;
            default: return -1;
        }
    }
}