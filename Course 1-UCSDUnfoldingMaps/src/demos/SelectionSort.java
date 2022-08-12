package demos;

public class SelectionSort {
	private static void swap(int[] vals, int index1, int index2) {
		int temp = vals[index1];
		vals[index1] = vals[index2];
		vals[index2] = temp;
	}
	
	public static void selectionSort(int[] vals) {
		int indexMin;
		for (int i = 0; i < vals.length - 1; i++) {
			indexMin = i;
			for (int j = i + 1; j < vals.length; j++) {
				if (vals[j] < vals[indexMin]) {
					indexMin = j;
				}
			}
			swap(vals, indexMin, i);
		}
	}
	
//	public static void main(String[] args) {
//		int[] vals = { 7, 6, 5, 4, 3, 2, 1 };
//		selectionSort(vals);
//		for (int i = 0; i < vals.length; i++) {
//			System.out.print(vals[i]);
//		}
//	}
}

/* With Selection Sort, sorting an already sorted array take the same amount time as sorting an unsorted array.
 * Selection sort gets no benefit from sorting an already sorted array, 
 * because its inner loop always runs to the end of the array to be sure its found the smallest remaining element.
 * */