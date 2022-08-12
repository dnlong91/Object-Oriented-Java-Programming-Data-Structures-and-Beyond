package demos;

public class BinarySearch {
	public static String findAirportCodeBS(String toFind, Airport[] airports) {
		int low = 0;
		int high = airports.length;
		int mid;
		
		while (low <= high) {
			mid = low + ((high - low) / 2);
			int compare = toFind.compareTo(airports[mid].getCity());
			if (compare < 0) {
				high = mid - 1;
			} else if (compare > 0) {
				low = mid + 1;
			} else {
				return airports[mid].getCode3();
			}
		}
		return null;
	}
}

//Time Complexity: O(logN)