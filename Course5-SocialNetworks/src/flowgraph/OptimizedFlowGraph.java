package flowgraph;

import util.FlowGraphLoader;

import java.util.*;

public class OptimizedFlowGraph extends FlowGraph {
	public OptimizedFlowGraph(HashSet<Integer> init, int rewardA, int rewardB) {
		super(init, rewardA, rewardB);
	}
	
	/*
	 This updateChanges() method is optimized with the idea of Kahn's Algorithm
	 */
	public void updateChanges() {
		// Print all members of currGeneration before updated
//		System.out.print("\ncurrGeneration before updated: ");
//		for (int vertex : currGeneration) {
//			System.out.print(vertex + " ");
//		}
		Queue<Integer> hasChanged = new LinkedList<Integer>();
		hasChanged.addAll(currGeneration);
//		System.out.println(vertices.size());
		while (!hasChanged.isEmpty()) {
			int vertex = hasChanged.poll();
			for (int neighbor : vertices.get(vertex)) {
				if (!currGeneration.contains(neighbor)) {
					if (getUpdated(neighbor)) {
						hasChanged.add(neighbor);
						currGeneration.add(neighbor);
					}
				}
			}
		}
		// Print all members of currGeneration after updated
//		System.out.print("\ncurrGeneration after updated: ");
//		for (int vertex : currGeneration) {
//			System.out.print(vertex + " ");
//		}
//		System.out.print("\nNo more updates\n");
	}

	private boolean getUpdated(int vertex) {
		boolean changesMade = false;
		HashSet<Integer> neighbors = vertices.get(vertex);
		int count = 0;
		for (int neighbor : neighbors) {
			if (currGeneration.contains(neighbor)) {
				count += 1;
			}
		}
		float p = (float)count / (float)neighbors.size();
		float q = (float)b / (float)(a + b);
		if (p > q) {
			changesMade = true;
			for (int neighbor : neighbors) {
				if (currGeneration.contains(neighbor)) {
					affects.get(neighbor).add(vertex);
				}
			}
		}
		return changesMade;
	}

	public void printEffectTable() {
		// TODO: Optimize this function
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
		OptimizedFlowGraph ofg = new OptimizedFlowGraph(init, 2, 1);
		// Load the graph
		System.out.print("Making a new optimized flow graph...");
		System.out.print("DONE. \nLoading the map...");
		FlowGraphLoader.loadFlowGraph(ofg, "Course5-SocialNetworks/data/small_test_graph.txt");
		System.out.println("DONE.");
		// Calculate new generations using Kahn algorithm
		long start = System.nanoTime();
		ofg.updateChanges();
		long end = System.nanoTime();
		long time = (end - start) / 1_000_000;
		System.out.println("\nkahnUpdateChanges run time: " + time);
//		ofg.printEffectTable();
		
		System.out.println("TEST 2:");
		Integer arr2[] = new Integer[5000];
		Random rand = new Random();
		for (int i = 0; i < 5000; i++) {
			arr2[i] = rand.nextInt(14947);
		}
		HashSet<Integer> init2 = new HashSet<Integer>(Arrays.asList(arr2));
		ofg = new OptimizedFlowGraph(init2, 2, 1);
		// Load the graph
		System.out.print("Making a new optimized flow graph...");
		System.out.print("DONE. \nLoading the map...");
		FlowGraphLoader.loadFlowGraph(ofg, "Course5-SocialNetworks/data/facebook_ucsd.txt");
		System.out.println("DONE.");
		// Calculate new generations using Kahn algorithm
		start = System.nanoTime();
		ofg.updateChanges();
		end = System.nanoTime();
		time = (end - start) / 1_000_000;
		System.out.println("\nkahnUpdateChanges run time: " + time);
//		ofg.printEffectTable();
	}
}