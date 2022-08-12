package demos;

public class BigOLectures {
	public static void reduce (int[] vals) {
		int minIndex =0;
		for (int i=0; i < vals.length; i++) {
			if (vals[i] < vals[minIndex] ) {
				minIndex = i;
		    }
		}
		int minVal = vals[minIndex];
		for (int i=0; i < vals.length; i++) {
			vals[i] = vals[i] - minVal;
			System.out.print(vals[i] + " ");
		}
	}
	
	public static int maxDifference (int[] vals) {
		int max = 0;
		for (int i = 0; i < vals.length; i++) {
			for (int j = 0; j < vals.length; j++) {
				if (vals[i] - vals[j] > max) {
					max = vals[i] - vals[j];
				}
			}
		}
		return max;
	}
	
	public static void main(String[] args) {
		//reduce(new int[] {1,2,5,3});
		
		System.out.print(maxDifference(new int[] {1,7,2,4,6,8}));
	}
}
