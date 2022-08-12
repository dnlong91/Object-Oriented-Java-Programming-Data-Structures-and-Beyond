/* How would you find the k-th smallest element in an array of integers?
 */

package casestudy;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class KthSmallestElement {
	/********* First Attempt *********/
	/* O(NlogN) */
	public int getKthSmallestElement1(int[] array, int k) {
		if (k <= 0 || k >= array.length) {
			throw new IllegalArgumentException("k must be a valid index of the array");
		}
		
		Arrays.sort(array);
		return array[k - 1];
	}
	
	/********* Second Attempt *********/
	/* O(NlogK) */
	public int getKthSmallestElement2(int[] array, int k) {
		if (k <= 0 || k >= array.length) {
			throw new IllegalArgumentException("k must be a valid index of the array");
		}
		
		PriorityQueue<Integer> smallestK = new PriorityQueue<Integer>(k, Collections.reverseOrder());
		
		for (int i = 0; i < Math.min(array.length, k); i++) {
			smallestK.add(array[i]);
		}
		
		for (int j = k; j < array.length; j++) {
			if (array[j] < smallestK.peek()) {
				smallestK.remove();
				smallestK.add(array[j]);
			}
		}
		
		return smallestK.peek();
	}
	
	/********* Third Attempt *********/
	/* O(N) */
	public int getKthSmallestElement3(int[] array, int k) {
		if (k <= 0 || k >= array.length) {
			throw new IllegalArgumentException("k must be a valid index of the array");
		}
		
		return getKthSmallestElement3(array, k, 0, array.length - 1);
	}

	private int getKthSmallestElement3(int[] array, int k, int left, int right) {
		int pivotIndex = getPivotIndex(array, left, right);
		
		if (pivotIndex == k - 1) {
			return array[pivotIndex];
		} else if (pivotIndex < k - 1) {
			return getKthSmallestElement3(array, k, pivotIndex + 1, right);
		} else {
			return getKthSmallestElement3(array, k, left, pivotIndex - 1);
		}
	}

	private int getPivotIndex(int[] array, int left, int right) {
		int pivotElement = array[right];
		int pivotIndex = left;
		
		for (int i = left; i <= right; i++) {
			if (array[i] < pivotElement) {
				int temp = array[i];
				array[i] = array[pivotIndex];
				array[pivotIndex] = temp;
				pivotIndex++;
			}
		}
		
		int temp = array[pivotIndex];
		array[pivotIndex] = array[right];
		array[right] = temp;
		return pivotIndex;
	}

	public static void main(String[] args) {
		int[] arr = new int[]{4, 5, 10, 11, 1, 3, 19, 8, 7};
		KthSmallestElement kth = new KthSmallestElement();
		System.out.println("6th smallest element is -> " + kth.getKthSmallestElement1(arr, 5));
		System.out.println("6th smallest element is -> " + kth.getKthSmallestElement2(arr, 5));
		System.out.println("6th smallest element is -> " + kth.getKthSmallestElement3(arr, 5));
	}
}
