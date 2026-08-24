package effibenchjmh.task3618;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int possibleStringCount(String word, int k) {
        final int MODULO = (int) (1e9 + 7);
        int wordLength = word.length();
        List<Integer> characterGroups = new ArrayList<>();
        
        for (int index = 0; index < wordLength;) {
            int characterCount = 1;
            char currentCharacter = word.charAt(index++);
            while (index < wordLength && word.charAt(index) == currentCharacter) {
                characterCount++;
                index++;
            }
            characterGroups.add(characterCount);
        }

        long totalCombinations = 1;
        for (int groupCount : characterGroups) {
            totalCombinations = (totalCombinations * groupCount) % MODULO;
        }

        if (k <= characterGroups.size()) {
            return (int) totalCombinations;
        }

        int maxSize = k - 1;
        int[] dp = new int[maxSize + 1];
        dp[0] = 1;

        for (int count : characterGroups) {
            int[] newDP = new int[maxSize + 1];
            long cumulativeSum = 0;
            for (int s = 0; s <= maxSize; s++) {
                if (s - 1 >= 0) cumulativeSum = (cumulativeSum + dp[s - 1]) % MODULO;
                if (s - count - 1 >= 0) cumulativeSum = (cumulativeSum - dp[s - count - 1] + MODULO) % MODULO;
                newDP[s] = (int) cumulativeSum;
            }
            dp = newDP;
        }

        long totalLessThanK = 0;
        for (int s = characterGroups.size(); s <= maxSize; s++) {
            totalLessThanK = (totalLessThanK + dp[s]) % MODULO;
        }

        return (int) ((totalCombinations - totalLessThanK + MODULO) % MODULO);
    }
}
