package flowgraph;

import java.util.*;

import util.FlowGraphLoader;

/**
 * @author Ginny Dang
 * 
 * Investigating the information flow within a social network
 */

public class FlowGraph {
	private HashSet<Integer> initGeneration;
	private HashSet<Integer> currGeneration;
	protected int a;
	protected int b;
	protected HashMap<Integer, HashSet<Integer>> vertices;
	protected HashMap<Integer, HashSet<Integer>> affects;

	public FlowGraph(HashSet<Integer> init, int rewardA, int rewardB) {
		initGeneration = new HashSet<Integer>();
		initGeneration.addAll(init);
		currGeneration = new HashSet<Integer>();
		currGeneration.addAll(init);
		a = rewardA;
		b = rewardB;
		vertices = new HashMap<Integer, HashSet<Integer>>();
		affects = new HashMap<Integer, HashSet<Integer>>();
	}

	public void addVertex(int num) {
		// Only add vertex if it has not been in the graph yet
		if (!vertices.containsKey(num)) {
			vertices.put(num, new HashSet<Integer>());
			affects.put(num, new HashSet<Integer>());
		}
	}

	public void addEdge(int from, int to) {
		// Error handling
		if (!vertices.containsKey(from) || !vertices.containsKey(to)) {
			throw new IllegalArgumentException("start point or end point does not exist in the map");
		}
		// Add new edge
		HashSet<Integer> fromNeighbors = vertices.get(from);
		fromNeighbors.add(to);
		vertices.put(from, fromNeighbors);
	}

	private boolean updateChanges() {
		initGeneration.addAll(currGeneration);
		System.out.print("\ninitGeneration: ");
		for (int i : initGeneration) {
			System.out.print(i + " ");
		}
		boolean changesMade = false;
		for (int vertex : vertices.keySet()) {
			if (initGeneration.contains(vertex)) {
				continue;
			}
//			System.out.print("\nConsidering " + vertex);
			HashSet<Integer> neighbors = vertices.get(vertex);
			float p = 0; // the fraction of v’s neighbors that have a specific behavior in the current generation
			int count = 0; // number of v’s neighbors that have a specific behavior in the current generation
			// Update p
			for (int neighbor : neighbors) {
				if (currGeneration.contains(neighbor)) {
					count += 1;
				}
			}
			// Determine if the current vertex is about to change the behavior or not
			p = (float)count / (float)neighbors.size();
			float q = (float)b / (float)(a + b);
//			System.out.print(" ~> p = " + p + " vs ");
//			System.out.print("b / (a + b) = " + q);
			if (p > q) {
				changesMade = true;
				currGeneration.add(vertex);
				for (int neighbor : neighbors) {
					if (currGeneration.contains(neighbor)) {
						affects.get(neighbor).add(vertex);
					}
				}
			}
		}
		System.out.print("\ncurrGeneration: ");
		for (int i : currGeneration) {
			System.out.print(i + " ");
		}
		return changesMade;
	}
	
	public void updateGenerations() {
		// Print all members of the initial generation
		System.out.print("\nGeneration 0: ");
		for (int vertex : initGeneration) {
			System.out.print(vertex + " ");
		}
		// Print all members of each of the next generations
		for (int i = 0; i < vertices.size(); i++) {
			boolean canUpdate = updateChanges();
			if (!canUpdate) {
				System.out.println("\nNo more updates");
				break;
			} else {
				System.out.print("\n-----------------------------");
				System.out.print("\nGeneration " + (i + 1) + ": ");
				printNewGeneration();
			}
		}
	}
	
	private void printNewGeneration() {
		for (int vertex : currGeneration) {
			if (!initGeneration.contains(vertex)) {
				System.out.print(vertex + " ");
			}
		}
	}
	
	public void printEffectTable() {
		System.out.printf("------------------------------------------------------%n");
		System.out.printf("|  %s  |   %s   |   %s   |%n", "VERTEX", "AFFECTED NEIGHBORS", "PERCENTAGE");
		System.out.printf("------------------------------------------------------%n");
		for (int vertex: affects.keySet()) {
			System.out.printf("| %8d | ", vertex);
			String neighborStr = "";
			for (int neighbor : affects.get(vertex)) {
				neighborStr += String.valueOf(neighbor) + " ";
			}
			System.out.printf("%-22s |", neighborStr);
			float percentage = ((float)affects.get(vertex).size() / (float)vertices.get(vertex).size()) * 100;
			System.out.printf("%14.2f%% |%n", percentage);
		}
	}

	public static void main(String[] args) {
		// Init the graph
//		System.out.println("TEST 1:");
////		Integer arr1[] = { 5, 6, 14, 1, 3, 8 };
//		Integer arr1[] = { 14, 1, 9 };
//		HashSet<Integer> init = new HashSet<Integer>(Arrays.asList(arr1));
//		FlowGraph fg = new FlowGraph(init, 2, 1);
//		// Load the graph
//		System.out.print("Making a new flow graph...");
//		System.out.print("DONE. \nLoading the map...");
//		FlowGraphLoader.loadFlowGraph(fg, "data/small_test_graph.txt");
//		System.out.println("DONE.");
//		// Calculate new generations
//		fg.updateGenerations();
//		fg.printEffectTable();
		
		System.out.println("TEST 2:");
		Integer arr2[] = new Integer[5000];
		Random rand = new Random();
		for (int i = 0; i < 5000; i++) {
			arr2[i] = rand.nextInt(14947);
		}
		HashSet<Integer> init2 = new HashSet<Integer>(Arrays.asList(arr2));
		FlowGraph fg = new FlowGraph(init2, 2, 1);
		// Load the graph
		System.out.print("Making a new optimized flow graph...");
		System.out.print("DONE. \nLoading the map...");
		FlowGraphLoader.loadFlowGraph(fg, "data/facebook_ucsd.txt");
		System.out.println("DONE.");
		// Calculate new generations
		long start = System.nanoTime();
		fg.updateChanges();
		long end = System.nanoTime();
		long time = (end - start) / 1_000_000;
		System.out.println("\nupdateChanges run time: " + time);
//		fg.printEffectTable();
	}
}