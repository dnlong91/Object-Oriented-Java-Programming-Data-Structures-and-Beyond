package flowgraph;

import java.util.HashSet;

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

	public boolean updateChanges() {
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
	
	// TODO: for testing purpose only
	public void updateGenerations() {
		// Print all members of the initial generation
//		System.out.print("\nGeneration 0: ");
//		for (int vertex : initGeneration) {
//			System.out.print(vertex + " ");
//		}
		// Print all members of each of the next generations
//		for (int i = 0; i < vertices.size(); i++) {
		while (updateChanges()) {
//			boolean canUpdate = updateChanges();
//			if (!canUpdate) {
//				System.out.println("\nNo more updates");
//				break;
//			} else {
//				System.out.print("\n-----------------------------");
//				System.out.print("\nGeneration " + (i + 1) + ": ");
//				printNewGeneration();
//			}
		}
		System.out.print("\nFinal number of affected vertices: " + currGeneration.size());
}

	// TODO: for testing purpose only
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
		int loopCount = 0; // TODO: for testing purpose
		for (int vertex: affects.keySet()) {
			if (loopCount >= 100) {
				break;
			}
			loopCount++;
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
		System.out.printf("------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
	}
}