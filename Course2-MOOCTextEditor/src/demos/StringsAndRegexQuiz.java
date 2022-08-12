package demos;

import java.util.ArrayList;

public class StringsAndRegexQuiz {
	// Question 2
	public static void tryEqualEqual() {
		String s1 = new String("String 1");
		String s2 = new String("String 1");
		if (s1 == s2) {
		    System.out.println("Equal");
		}
		else {
		    System.out.println("Not equal");
		}
	}
	
	// Question 4
	public static void tryConcat() {
		String s = "Hello";
		s.concat(" World!");
		System.out.println(s);
	}
	
	// Question 5
	public static void tryIndexOf() {
		String text = "Practice, practice, practice!";
		System.out.println(text.indexOf("prac"));
	}
	
	// Question 6
	public static void trySplitWord() {
		String text = "Hurray!!#! It's Friday! finally...";
		String[] words = text.split("!+");
		
		for (int i = 0; i < words.length; i++) {
			System.out.print(words[i] + " ");
		}
	}

	public static void main(String[] args) {
		// Test method 1
		/* The == operator compares object references.
		 * Since two objects are created, their references are not the same.
		 * To compare the actual characters in the Strings, you need to use the .equals method.
		 * */
		tryEqualEqual();
		
		// Test method 2
		/* Strings are immutable.
		 * So the call to s.concat returns a new string (which is ignored), but does not modify s.
		 * */
		tryConcat();
		
		// Test method 3
		tryIndexOf();
		
		// Test method 4
		/* Notice that all of the characters between one or more ! are preserved as separate Strings.
		 * */
		trySplitWord();
	}
}
