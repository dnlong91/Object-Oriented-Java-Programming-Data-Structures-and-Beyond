package flowgraph;

import util.FlowGraphLoader;

import java.util.*;

/**
 * @author Ginny Dang
 * 
 * Investigating the information flow within a social network
 */

public class BasicFlowGraph extends FlowGraph {
	private HashSet<Integer> initGeneration;

	public BasicFlowGraph(HashSet<Integer> init, int rewardA, int rewardB) {
		super(init, rewardA, rewardB);
		initGeneration = new HashSet<Integer>();
		initGeneration.addAll(init);
	}

	private boolean updateChanges() {
		initGeneration.addAll(currGeneration);
//		System.out.print("\ninitGeneration: ");
//		for (int i : initGeneration) {
//			System.out.print(i + " ");
//		}
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
//		System.out.print("\ncurrGeneration: ");
//		for (int i : currGeneration) {
//			System.out.print(i + " ");
//		}
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
		System.out.printf("------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
		System.out.printf("|  %s  |   %s   |   %s   |   %s   |   %s   |%n", "VERTEX", "                             AFFECTED NEIGHBORS                            ", "AFFECTED NEIGHBOR COUNT", "NEIGHBOR COUNT", "PERCENTAGE");
		System.out.printf("------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
		for (int vertex: affects.keySet()) {
			String neighborStr = "";
			int affectedNeighborCount = affects.get(vertex).size();
			int neighborCount = vertices.get(vertex).size();
			float percentage = ((float)affectedNeighborCount / (float)neighborCount) * 100;
			// Vertex Column
			System.out.printf("| %8d | ", vertex);
			// Affected Neighbors Column
			for (int neighbor : affects.get(vertex)) {
				neighborStr += String.valueOf(neighbor) + " ";
				if (neighborStr.length() >= 80) {
					break;
				}
			}
//			System.out.printf("%-79s |", neighborStr);
			if (neighborStr.length() < 80) {
				System.out.printf("%-79s |", neighborStr);
			} else {
				System.out.printf("%-79s |", neighborStr.substring(0, 76) + "...");
			}
			// Affected Neighbor Count Column
			System.out.printf("%28d |", affectedNeighborCount);
			// Neighbor Count Column
			System.out.printf("%19d |", neighborCount);
			// Percentage Column
			System.out.printf("%14.2f%% |%n", percentage);
		}
	}

	public static void main(String[] args) {
		// Init the graph
		System.out.println("TEST 1:");
//		Integer arr1[] = { 5, 6, 14, 1, 3, 8 };
		Integer arr1[] = { 14, 1, 9 };
		HashSet<Integer> init = new HashSet<Integer>(Arrays.asList(arr1));
		BasicFlowGraph bfg = new BasicFlowGraph(init, 2, 1);
		// Load the graph
		System.out.print("Making a new basic flow graph...");
		System.out.print("DONE. \nLoading the map...");
		FlowGraphLoader.loadFlowGraph(bfg,"Course5-SocialNetworks/data/small_test_graph.txt");
		System.out.println("DONE.");
		// Calculate new generations
		long start = System.nanoTime();
//		bfg.updateGenerations();
		bfg.updateChanges();
		long end = System.nanoTime();
		long time = (end - start) / 1_000_000;
		System.out.println("\nupdateChanges run time: " + time);
		bfg.printEffectTable();
		
		System.out.println("TEST 2:");
		Integer arr2[] = new Integer[5000];
		Random rand = new Random();
		for (int i = 0; i < 5000; i++) {
			arr2[i] = rand.nextInt(14947);
		}
		HashSet<Integer> init2 = new HashSet<Integer>(Arrays.asList(arr2));
		bfg = new BasicFlowGraph(init2, 2, 1);
		// Load the graph
		System.out.print("Making a new basic flow graph...");
		System.out.print("DONE. \nLoading the map...");
		FlowGraphLoader.loadFlowGraph(bfg, "Course5-SocialNetworks/data/facebook_ucsd.txt");
		System.out.println("DONE.");
		// Calculate new generations
		start = System.nanoTime();
		bfg.updateChanges();
		end = System.nanoTime();
		time = (end - start) / 1_000_000;
		System.out.println("\nupdateChanges run time: " + time);
		bfg.printEffectTable();
	}
}