package effibenchjmh.task3194;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
	public List<Integer> findWordsContaining(String[] words, char x) {
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<words.length; i++){
			if(words[i].indexOf(x) != -1){
				list.add(i);
			}
		}
		return list;
	}
}
