class Solution {
    public List<Integer> beautifulIndices(String s, String a, String b, int k) {
        
        List<Integer> aArray  =  construct(s,a);
        List<Integer> bArray =  construct(s,b);

        List<Integer> answer =  new ArrayList<>();
        int i=0;
        for (int ai : aArray) {
            int lo = ai - k;
            int idx = java.util.Collections.binarySearch(bArray, lo);
            if (idx < 0) idx = -idx - 1;
            if (idx < bArray.size() && bArray.get(idx) <= ai + k) {
                answer.add(ai);
            }
        }
        return answer;
    }

   private List<Integer> construct(String s, String pattern) {
    String combined = pattern + "#" + s;
    int[] array = new int[combined.length()];
    
    int i = 1, len = 0;

    while (i < combined.length()) {
        if (combined.charAt(i) == combined.charAt(len)) {
            len++;
            array[i] = len;
            i++;
        } else {
            if (len != 0) {
                len = array[len - 1];
            } else {
                i++;
            }
        }
    }

    List<Integer> list = new ArrayList<>();
    for (i = pattern.length() + 1; i < combined.length(); i++) {
        if (array[i] == pattern.length()) {
            list.add(i - 2 * pattern.length());  // Corrected index calculation
        }
    }

    return list;
}

}