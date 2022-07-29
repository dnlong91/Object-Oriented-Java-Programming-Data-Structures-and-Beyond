package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 * @author Ginny Dang
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		size = 0;
		head = new LLNode<E> (null);
		tail = new LLNode<E> (null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param i The element to add
	 */
	public boolean add(E i) {
		// Edge case
		if (i == null) {
			throw new NullPointerException("Null Elements Are Not Allowed");
		}
		// Add an element to an empty list
		LLNode<E> node = new LLNode<E> (i);
		node.next = tail;
		if (size == 0) {
			tail.prev = node;
			node.prev = head;
			head.next = node;
		}
		// Add an element to a list with already existing elements
		else {
			node.prev = tail.prev;
			tail.prev = node;
			node.prev.next = node;
		}
		size += 1;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) {
		// Edge cases
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Out Of Bounds");
		}
		// Normal cases
		LLNode<E> curr = head.next;
		for (int i = 0; i < index; i++) {
			curr = curr.next;
		}
		return curr.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param j The element to add
	 */
	public void add(int index, E j) {
		// Edge cases
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Out Of Bounds");
		}
		if (j == null) {
			throw new NullPointerException("Null Elements Are Not Allowed");
		}
		// Add to the end of the list
		if (index == size) {
			add(j);
		} 
		// Normal cases
		else {
			LLNode<E> node = new LLNode<E> (j);
			// Determine where to insert
			LLNode<E> curr = head.next;
			for (int i = 0; i < index - 1; i++) {
				curr = curr.next;
			}
			// Add the new node right after curr
			node.next = curr.next;
			node.prev = curr;
			node.next.prev = node;
			node.prev.next = node;
			size += 1;
		}
	}

	/** Return the size of the list */
	public int size() {
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {
		// Edge cases
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Out Of Bounds");
		}
		// Normal cases
		// Determine where to remove
		LLNode<E> curr = head.next;
		for (int i = 0; i < index; i++) {
			curr = curr.next;
		}
		curr.prev.next = curr.next;
		curr.next.prev = curr.prev;
		curr.next = null;
		curr.prev = null;
		size -= 1;
		return curr.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The old value of the element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) {
		// Edge cases
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Out Of Bounds");
		}
		if (element == null) {
			throw new NullPointerException("Null Elements Are Not Allowed");
		}
		// Normal cases
		//Determine where to set the new values
		LLNode<E> curr = head.next;
		for (int i = 0; i < index; i++) {
			curr = curr.next;
		}
		E oldValue = curr.data;
		curr.data = element;
		return oldValue;
	}
}

class LLNode<E> {
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) {
		this.data = e;
		this.prev = null;
		this.next = null;
	}
}
