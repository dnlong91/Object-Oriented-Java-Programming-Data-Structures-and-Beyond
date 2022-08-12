/* For the prompt: Given a list of numbers from 0 to n, with exactly one number removed, find the missing number.
 * 
 * Optimal solution: We can leverage the known formula for the sum of integers from 0 to n.
 * This solution runs in O(n). Notice that the prompt does not explicitly state that the given list is sorted.
 * So it is important to formulate solution without making an assumption.  
 * */

package practicecodingquestions;

import java.util.ArrayList;

public class MissingNumber {
	public int findMissing(ArrayList<Integer> nums) {
		int sum = 0;
		int n = nums.size();
		for (int k=0; k < n; k++) {
			sum += nums.get(k);
		}
		return (n * (n + 1) / 2 - sum);
	}
}