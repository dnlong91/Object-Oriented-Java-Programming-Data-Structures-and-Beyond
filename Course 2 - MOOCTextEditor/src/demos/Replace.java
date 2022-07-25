/* 3 different functions in this class are 3 attempts to replace every occurrence of a specific character in a string with another character
 * 
 * Specifically, we'll be given 2 input characters "here" and "gone" and a string "word". Our goal is to replace all occurrence of "here" with "gone"
 * and then return the modified version of "word" after finishing all replacements.
 * 
 * For example, given the word "a happy", after replacing all 'a' with 'i', we'll get "i hippy"
 * */

package demos;

public class Replace {
	public static String replaceAttempt1(String word, char gone, char here) {
		char[] cArray = word.toCharArray(); // word.toCharArray() returns a reference to a copy of the char array that is stored in the String word
		for (char c : cArray) {
			if (c == gone) {
				c = here;
			}
		}
		return word;
	}

	public static String replaceAttempt2(String word, char gone, char here) {
		char[] cArray = word.toCharArray(); // word.toCharArray() returns a reference to a copy of the char array that is stored in the String word
		for (char c : cArray) {
			if (c == gone) {
				c = here;
			}
		}
		return new String (cArray);
	}
	
	public static String replaceAttempt3(String word, char gone, char here) {
		char[] cArray = word.toCharArray();
		char[] cArrayMod = new char[cArray.length];
		int i = 0;
		for (char c : cArray) {
			if (c == gone) {
				cArrayMod[i] = here;
			} else {
				cArrayMod[i] = c;
			}
			i++;
		}
		return new String(cArrayMod);
	}
	
	public static void main(String[] args) {
		// Test attempt 1
		/* This attempt does not work since inside the loop, 
		 * what we've been working with was just a copy of the char array that is stored in the String word, 
		 * not the String word itself.
		 * Therefore, when we return the String word, it does not change at all */
		System.out.println("Attemp 1 result: " + replaceAttempt1("a happy", 'a', 'i'));
		
		// Test attempt 2
		/* This attempt does not work since all of our modifications, all of our assignment statements 
		 * have just changed the contents of a variable 'c', not inside the character array cArray
		 * Therefore, when we return a new String made from cArray, nothing changes at all */
		System.out.println("Attemp 2 result: " + replaceAttempt2("a happy", 'a', 'i'));
		
		// Test attempt 3
		/* This attempt works because we constructed a new String from a new character array created over the old one which is cArray,
		 * after some replacements.
		 * */
		System.out.println("Attemp 3 result: " + replaceAttempt3("a happy", 'a', 'i'));
		
		// However, after all those works done, the original input String has not been changed at all since Strings in Java are immutable
	}
}
