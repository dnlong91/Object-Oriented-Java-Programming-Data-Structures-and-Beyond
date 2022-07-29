/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {
	private static final int LONG_LIST_LENGTH = 10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++) {
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
	}

	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet() {
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i < LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check " + i + " element", (Integer) i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
	}
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove() {
		// Remove
		// test removing at an invalid index
		try {
			emptyList.remove(0);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		try {
			shortList.remove(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		try {
			longerList.remove(11);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		// test removing at a valid index
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		try {
			assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		} catch (IndexOutOfBoundsException e) {
			
		}
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		String b = shortList.remove(1);
		assertEquals("Remove: check b is correct ", "B", b);
		try {
			assertEquals("Remove: check element 1 is correct ", null, shortList.get(1));
		} catch (IndexOutOfBoundsException e) {
			
		}
		assertEquals("Remove: check size is correct ", 1, shortList.size());
		
		int c = longerList.remove(7);
		assertEquals("Remove: check c is correct ", 7, c);
		try {
			assertEquals("Remove: check element 7 is correct ", (Integer)8, longerList.get(7));
		} catch (IndexOutOfBoundsException e) {
			
		}
		assertEquals("Remove: check size is correct ", 9, longerList.size());
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd() {
		// test adding a null element, add should throw an exception
		try {
			emptyList.add(null);
			fail("Check null element");
		} catch (NullPointerException e) {
			
		}
		try {
			shortList.add(null);
			fail("Check null element");
		} catch (NullPointerException e) {
			
		}
		try {
			longerList.add(null);
			fail("Check null element");
		} catch (NullPointerException e) {
			
		}
		// test adding a value
		boolean stateEmptyList = emptyList.add(10);
		assertEquals("AddEnd: check state is true", true, stateEmptyList);
		
		boolean stateShortList = shortList.add("24");
		assertEquals("AddEnd: check state is true", true, stateShortList);
		
		boolean stateLongerList = longerList.add(2000);
		assertEquals("AddEnd: check state is true", true, stateLongerList);
	}
	
	/** Test the size of the list */
	@Test
	public void testSize() {
		// Add
		// test adding to the end
		emptyList.add(10);
		int lengthEmptyList = emptyList.size();
		assertEquals("AddEnd: check length of emptyList", 1, lengthEmptyList);
		
		shortList.add("24");
		int lengthShortList = shortList.size();
		assertEquals("AddEnd: check length of shortList" , 3, lengthShortList);
		
		longerList.add(2000);
		int lengthLongerList = longerList.size();
		assertEquals("AddEnd: check length of longerList" , 11, lengthLongerList);
		
		// test adding to a specific position
		emptyList.add(0, 24);
		lengthEmptyList = emptyList.size();
		assertEquals("AddEnd: check length of emptyList", 2, lengthEmptyList);
		
		shortList.add(1, "Ginny");
		lengthShortList = shortList.size();
		assertEquals("AddEnd: check length of shortList" , 4, lengthShortList);
		
		longerList.add(7, 2000);
		lengthLongerList = longerList.size();
		assertEquals("AddEnd: check length of longerList" , 12, lengthLongerList);
		
		// Remove
		// test removing at an invalid index
		try {
			emptyList.remove(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		try {
			shortList.remove(5);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		try {
			longerList.remove(20);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		
		// test removing at a valid index
		emptyList.remove(0);
		lengthEmptyList = emptyList.size();
		assertEquals("AddEnd: check length of emptyList", 1, lengthEmptyList);
		
		shortList.remove(3);
		lengthShortList = shortList.size();
		assertEquals("AddEnd: check length of shortList" , 3, lengthShortList);
		
		longerList.remove(5);
		lengthLongerList = longerList.size();
		assertEquals("AddEnd: check length of longerList" , 11, lengthLongerList);
	}
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex() {
		// Add at index
        // test adding at an invalid index
		try {
			emptyList.add(-1, 1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		try {
			shortList.add(LONG_LIST_LENGTH, "2");
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		try {
			longerList.add(LONG_LIST_LENGTH + 2, 3);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		// test adding a null element, add should throw an exception
		try {
			emptyList.add(0, null);
			fail("Check null element");
		} catch (NullPointerException e) {
			
		}
		try {
			shortList.add(1, null);
			fail("Check null element");
		} catch (NullPointerException e) {
					
		}
		try {
			longerList.add(2, null);
			fail("Check null element");
		} catch (NullPointerException e) {
					
		}
		// test adding a element at a valid index
		emptyList.add(0, 24);
		int newEleEmptyList = emptyList.get(0);
		assertEquals("AddEnd: new element of emptyList", 24, newEleEmptyList);
		
		shortList.add(1, "Ginny");
		String newEleShortList = shortList.get(1);
		assertEquals("AddEnd: new element of shortList", "Ginny", newEleShortList);
		
		longerList.add(7, 2000);
		int newEleLongerList = longerList.get(7);
		assertEquals("AddEnd: new element of longerList", 2000, newEleLongerList);
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet() {
	    // Set 
		// test setting at an invalid index
		try {
			emptyList.set(0, 1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		try {
			shortList.set(-1, "200");
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		try {
			longerList.set(LONG_LIST_LENGTH + 2, 3);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
			
		}
		// test setting a null element, add should throw an exception
		try {
			shortList.set(1, null);
			fail("Check null element");
		} catch (NullPointerException e) {
							
		}
		try {
			longerList.set(2, null);
			fail("Check null element");
		} catch (NullPointerException e) {
					
		}
		// test setting a non-null element at a valid index
		String oldDataShortList = shortList.set(1, "Ginny");
		assertEquals("Set: old element of shortList", "B", oldDataShortList);
		assertEquals("Set: new element of shortList", "Ginny", shortList.get(1));
		
		int oldDataLongerList = longerList.set(9, 100);
		assertEquals("Set: old element of longerList", 9, oldDataLongerList);
		assertEquals("Set: new element of longerList", 100, (int)longerList.get(9));
	}
}