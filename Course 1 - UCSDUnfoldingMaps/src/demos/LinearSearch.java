package demos;

public class LinearSearch {
	// toFind a city name
	public static String findAirportCode(String toFind, Airport[] airports) {
		int index = 0;
		while (index < airports.length) {
			Airport a = airports[index];
			if (toFind.equals(a.getCity())) {
				return a.getCode3();
			}
			index++;
		}
		return null;
	}
}

// Time Complexity: O(N)