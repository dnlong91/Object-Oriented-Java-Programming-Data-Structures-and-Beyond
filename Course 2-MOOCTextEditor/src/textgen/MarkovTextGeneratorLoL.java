package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 * @author Ginny Dang
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {
	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator) {
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText) {
		/* set "starter" to be the first word in the text  
		 * set "prevWord" to be starter
		 * for each word "w" in the source text starting at the second word
		 *     check to see if "prevWord" is already a node in the list
		 *         if "prevWord" is a node in the list
		 *             add "w" as a nextWord to the "prevWord" node
		 *         else
		 *             add a node to the list with "prevWord" as the node's word
		 *             add "w" as a nextWord to the "prevWord" node
		 *     set "prevWord" = "w"
		 * add starter to be a next word for the last word in the source text.
		 **/
		List<String> words = new ArrayList<String>();
		// Keep all existing words in wordList from previous trains
		if (!wordList.isEmpty()) {
			for (ListNode wordNode : wordList) {
				words.add(wordNode.getWord());
			}
		}
		// Add all new words from sourceText
		String[] newWords = sourceText.split("[\\s]+");
		for (String newWord : newWords) {
			words.add(newWord);
		}
		starter = words.get(0);
		String prevWord = starter;
		for (String word : words) {
			ListNode prevWordNode = getExistingNode(prevWord);
			// if "prevWord" is a node in the list
			if (prevWordNode != null) {
				prevWordNode.addNextWord(word);
			}
			// if "prevWord" is not a node in the list
			else {
				ListNode newWordNode = new ListNode(prevWord);
				wordList.add(newWordNode);
				newWordNode.addNextWord(word);
			}
			prevWord = word;
		}
		// add starter to be a next word for the last word in the source text
		ListNode lastWordNode = new ListNode(words.get(words.size() - 1));
		wordList.add(lastWordNode);
		lastWordNode.addNextWord(starter);
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    /* set "currWord" to be the starter word
	     * set "output" to be ""
	     * add "currWord" to output
	     * while you need more words
	     *     find the "node" corresponding to "currWord" in the list
	     *     select a random word "w" from the "wordList" for "node"
	     *     add "w" to the "output"
	     *     set "currWord" to be "w" 
	     *     increment number of words added to the list
	     * */
		if (wordList.isEmpty() || numWords == 0) {
			return "";
		}
		String currWord = starter;
		String output = "";
		output += currWord + " ";
		int addedWords = 1;
//		while (addedWords < numWords && addedWords <= wordList.size()) {
		while (addedWords < numWords) {
			ListNode node = getExistingNode(currWord);
			String w = node.getRandomNextWord(rnGenerator);
			output += w + " ";
			currWord = w;
			addedWords += 1;
		}
		return output;
	}
	
	// Can be helpful for debugging
	@Override
	public String toString() {
		String toReturn = "";
		for (ListNode n : wordList) {
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText) {
		wordList.clear();
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = new Random();
		train(sourceText);
	}
	
	private ListNode getExistingNode(String word) {
		for (ListNode wordNode : wordList) {
			if (wordNode.getWord().equalsIgnoreCase(word)) {
				return wordNode;
			}
		}
		return null;
	}
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args) {
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}
}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode {
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word) {
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord() {
		return word;
	}

	public void addNextWord(String nextWord) {
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator) {
		int nextWordIndex = generator.nextInt(this.nextWords.size());
		String nextWord = this.nextWords.get(nextWordIndex);
	    return nextWord;
	}

	public String toString() {
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
}