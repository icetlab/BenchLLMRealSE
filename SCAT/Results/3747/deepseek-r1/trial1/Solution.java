class Solution {
   public int maxAdjacentDistance(int[] nums) {
		 int i=0;
		 
		 List<Integer> l=new ArrayList<>();
		 for(i=0;i<nums.length;i++) {
			 int j = (i + 1) % nums.length;
			int r= Math.abs(nums[i]-nums[j]) ;
			l.add(r);
				
		 }
		 int cir=nums[0]-nums[nums.length-1];
		 l.add(cir);
		 int[] array = new int[l.size()];
	        for (int k = 0; k < l.size(); k++) {
	            array[k] = l.get(k);
	        }
	        // Arrays.sort(array);
	        
		 
		 
		 
		return java.util.Collections.max(l);
	        
	    }
}