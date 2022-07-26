package document;

import java.util.List;

/** 
 * A naive implementation of the Document abstract class. 
 * @author UC San Diego Intermediate Programming MOOC team
 * @author Ginny Dang
 * Date: July 25th, 2022
 * 
 */
public class BasicDocument extends Document {
	/** Create a new BasicDocument object
	 * 
	 * @param text The full text of the Document.
	 */
	public BasicDocument(String text) {
		super(text);
	}
	
	/**
	 * Get the number of words in the document.
	 * A "word" is defined as a contiguous string of alphabetic characters
	 * i.e. any upper or lower case characters a-z or A-Z.  This method completely 
	 * ignores numbers when you count words, and assumes that the document does not have 
	 * any strings that combine numbers and letters. 
	 * 
	 * Check the examples in the main method below for more information.
	 * 
	 * This method should process the entire text string each time it is called.  
	 * 
	 * @return The number of words in the document.
	 */
	@Override
	public int getNumWords() {
		// Edge case
	    if (getText().length() == 0) {
	    	return 0;
	    }
	    // Normal cases
	    List<String> words = getTokens("[a-zA-Z]+");
//	    for (int i = 0; i < words.size(); i++) {
//			System.out.println(words.get(i));
//		}
//		System.out.println("\n" + words.size());
        return words.size();
	}
	
	/**
	 * Get the number of sentences in the document.
	 * Sentences are defined as contiguous strings of characters ending in an 
	 * end of sentence punctuation (. ! or ?) or the last contiguous set of 
	 * characters in the document, even if they don't end with a punctuation mark.
	 * 
	 * Check the examples in the main method below for more information.  
	 * 
	 * This method should process the entire text string each time it is called.  
	 * 
	 * @return The number of sentences in the document.
	 */
	@Override
	public int getNumSentences() {
		// Edge case
	    if (getText().length() == 0) {
	    	return 0;
	    }
	    // Normal cases
	    List<String> sentences = getTokens("[^!?.]+");
//	    for (int i = 0; i < sentences.size(); i++) {
//			System.out.println(sentences.get(i));
//		}
//		System.out.println("\n" + sentences.size());
        return sentences.size();
	}
	
	/**
	 * Get the total number of syllables in the document (the stored text). 
	 * To count the number of syllables in a word, it uses the following rules:
	 *       Each contiguous sequence of one or more vowels is a syllable, 
	 *       with the following exception: a lone "e" at the end of a word 
	 *       is not considered a syllable unless the word has no other syllables. 
	 *       You should consider y a vowel.
	 *       
	 * Check the examples in the main method below for more information.  
	 * 
	 * This method should process the entire text string each time it is called.  
	 * 
	 * @return The number of syllables in the document.
	 */
	@Override
	public int getNumSyllables() {
		// Edge case
	    if (getText().length() == 0) {
	    	return 0;
	    }
	    // Normal cases
	    // Gather word only, no special characters, no numbers in each word
	    String cleanText = getText().replaceAll("[^a-zA-Z0-9]", " ");
	    String[] words = cleanText.split(" +");
	    // Get total number of Syllables of the whole text
	    int syllableCount = 0;
	    for (int i = 0; i < words.length; i++) {
	    	if (!words[i].matches(".*\\d.*")) {
	    		syllableCount += countSyllables(words[i]);
	    		//System.out.println(words[i]);
	    		//System.out.println(countSyllables(words[i]));
	    	}
	    }
        return syllableCount;
	}
	
	/* The main method for testing this class. 
	 * You are encouraged to add your own tests.  */
	public static void main(String[] args) {
		/* Each of the test cases below uses the method testCase.  The first 
		 * argument to testCase is a Document object, created with the string shown.
		 * The next three arguments are the number of syllables, words and sentences 
		 * in the string, respectively.  You can use these examples to help clarify 
		 * your understanding of how to count syllables, words, and sentences.
		 */
		testCase(new BasicDocument("This is a test.  How many???  "
		        + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
				16, 13, 5);
		testCase(new BasicDocument(""), 0, 0, 0);
		testCase(new BasicDocument("sentence, with, lots, of, commas.!  "
		        + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
		testCase(new BasicDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
		testCase(new BasicDocument("Here is a series of test sentences. Your program should "
				+ "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
				+ "the correct amount of syllables (example, for example), "
				+ "but most of them will."), 49, 33, 3);
		testCase(new BasicDocument("Segue"), 2, 1, 1);
		testCase(new BasicDocument("Sentence"), 2, 1, 1);
		testCase(new BasicDocument("Sentences?!"), 3, 1, 1);
		testCase(new BasicDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
		         32, 15, 1);
	}
}