package demos;

public class InsertionSort {
	private static void swap(int[] vals, int index1, int index2) {
		int temp = vals[index1];
		vals[index1] = vals[index2];
		vals[index2] = temp;
	}
	
	public static void insertionSort(int[] vals) {
		int currInd;
		for (int pos = 1; pos < vals.length; pos++) {
			currInd = pos;
		    while (currInd > 0 && vals[currInd] < vals[currInd-1]) {
		    	swap(vals, currInd, currInd-1);
		    	currInd -= 1;
		    }
		}
	}
	
//	public static void main(String[] args) {
//		int[] vals = { 7, 6, 5, 4, 3, 2, 1 };
//		insertionSort(vals);
//		for (int i = 0; i < vals.length; i++) {
//			System.out.print(vals[i]);
//		}
//	}
}

/* Sorting {7, 6, 5, 4, 3, 2, 1} would take more steps than sorting {0, 1, 2, 3, 4, 5, 6, 7}  
 * {7, 6, 5, 4, 3, 2, 1} is in the reverse order.
 * It would take many steps because each element would have to be inserted (swapped) all the way to the front every time.
 * The already sorted list above would be fast for insertion sort because the while loop would terminate early every time because the element to be inserted is always more than the elements lower than it in the array.  
 */