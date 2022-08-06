/* For the prompt: Given list of n numbers from 0 to 1000, find the number that occurs most often (frequently).
 * 
 * Optimal solution: since the numbers in the list are bounded by 1000, we can use them to index an array where we accumulate counts.
 * This solution runs in O(n).
 * */

package practicecodingquestions;

import java.util.ArrayList;

public class ModeElement {
	public int findMode(ArrayList<Integer> nums, int bound) {
	  // Java will automatically initalize this counts array to contain all 0s
	  int[] counts = new int[bound];
	  
	  for (int k = 0; k < nums.size(); k++) {
		  counts[nums.get(k)] ++;
	  }
	  int maxFreq = counts[0];
	  int mode = 0;
	  for (int k = 1; k < counts.length; k++) {
		  if (counts[k] > maxFreq) {
			  maxFreq = counts[k];
			  mode = k;
		  }
	  }
	  return mode;
	}
}