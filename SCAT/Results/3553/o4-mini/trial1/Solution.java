class Solution {
    public boolean checkTwoChessboards(String coordinate1, String coordinate2) {
        return ((coordinate1.charAt(0)-'a') + (coordinate1.charAt(1)-'1')) % 2 == ((coordinate2.charAt(0)-'a') + (coordinate2.charAt(1)-'1')) % 2;

    }
}