/* Implement a method to convert a string to number
 */

package casestudy;

public class StringToInteger {
	public int strToNum(String input) {
		if (input == null || input.equals("")) {
			throw new IllegalArgumentException("Must be a valid number string");
		}
		char[] charArr = input.toCharArray();
		int value = 0;
		for (int i = 0; i < charArr.length; i++) {
			value = value * 10 + Character.getNumericValue(charArr[i]);
		}
		return value;
	}
	
	public static void main(String[] args) {
		StringToInteger strToInt = new StringToInteger();
		
		String input = "123";
		int output = strToInt.strToNum(input);
		System.out.println(output);
		
		input = "154000";
		output = strToInt.strToNum(input);
		System.out.println(output);
		
		input = "";
		output = strToInt.strToNum(input);
		System.out.println(output);
	}
}