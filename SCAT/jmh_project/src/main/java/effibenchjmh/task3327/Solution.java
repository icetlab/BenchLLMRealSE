package effibenchjmh.task3327;

import java.util.*;
import java.util.stream.*;

class Solution {
		public long minimumMoves(int[] nums, int k, int maxChanges) {
			int n = nums.length;
			int[] cum = new int[n+1];
			for(int i = 0;i < n;i++){
				cum[i+1] = cum[i] + nums[i];
			}

			long[] wcum = new long[n+1];
			for(int i = 0;i < n;i++){
				wcum[i+1] = wcum[i] + (long)nums[i] * i;
			}

			long ans = Long.MAX_VALUE;
			for(int i = 0;i < n;i++){
				int low = -1, high = n + 1;
				while(high - low > 1){
					int h = high+low>>1;
					int pick = cum[Math.min(n, i+h+1)] - cum[Math.max(0, i-h)] + (h >= 2 ? maxChanges : 0);
					if(pick >= k){
						high = h;
					}else{
						low = h;
					}
				}
				int rem = k;
				long cost = 0;
				if(rem > 0 && nums[i] == 1){
					rem--;
				}
				if(rem > 0 && high >= 1 && i-1 >= 0 && nums[i-1] == 1){
					rem--;
					cost++;
				}
				if(rem > 0 && high >= 1 && i+1 < n && nums[i+1] == 1){
					rem--;
					cost++;
				}
				if(high >= 2){
					int use = Math.min(rem, maxChanges);
					rem -= use;
					cost += 2L*use;

					int u = 0;
					{
						int hh = Math.min(n, i + high + 1);
						int ll = Math.min(n, i + 2);
						cost += wcum[hh] - wcum[ll] - (long) i * (cum[hh] - cum[ll]);
						u += cum[hh] - cum[ll];
					}
					{
						int hh = Math.max(0, i-1);
						int ll = Math.max(0, i-high);
						cost += -(wcum[hh] - wcum[ll]) + (long) i * (cum[hh] - cum[ll]);
						u += cum[hh] - cum[ll];
					}

					assert u >= rem;
					cost -= (long)high * (u-rem);
				}
				ans = Math.min(ans, cost);
			}
			return ans;
		}

	}
