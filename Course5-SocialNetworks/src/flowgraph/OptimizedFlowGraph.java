package flowgraph;

import java.util.*;

public class OptimizedFlowGraph extends FlowGraph {
	private int[] countAffectedNeighbors = new int[15000];

	public OptimizedFlowGraph(HashSet<Integer> init, int rewardA, int rewardB) {
		super(init, rewardA, rewardB);
	}
	
	/*
	 This updateChanges() method is optimized with the idea of Kahn's Algorithm
	 */
	public void updateChanges() {
		float q = (float)b / (float)(a + b);
		// Initialize all affected neighbor counts
		for (int vertex : vertices.keySet()) {
			countAffectedNeighbors[vertex] = 0;
		}
		// Print all members of currGeneration before updated
//		System.out.print("\ncurrGeneration before updated: ");
//		for (int vertex : currGeneration) {
//			System.out.print(vertex + " ");
//		}

		/*
		Instead of using Queue, use an array with 2 pointers
		to reduce the use of complicated data structures for optimization purpose
		 */
		int[] hasChanged = new int[15000];
		int left = 0;
		int right = -1;
		for (int vertex : currGeneration) {
			hasChanged[right + 1] = vertex;
			right++;
		}
//		System.out.println(vertices.size());
		while (left <= right) {
			int vertex = hasChanged[left];
			left++;
			for (int neighbor : vertices.get(vertex)) {
				countAffectedNeighbors[neighbor] += 1;
				if (!currGeneration.contains(neighbor)) {
					// Determine if neighbor gets affected or not
					float p = (float)countAffectedNeighbors[neighbor] / (float)vertices.get(neighbor).size();
					if (p > q) {
						hasChanged[right + 1] = neighbor;
						right++;
						currGeneration.add(neighbor);
						for (int nextNeighbor : vertices.get(neighbor)) {
							if (currGeneration.contains(nextNeighbor)) {
								affects.get(nextNeighbor).add(neighbor);
							}
						}
					}
				}
			}
		}
		// Print all members of currGeneration after updated
		System.out.println("Final number of affected vertices: " + currGeneration.size());
//		System.out.print("\ncurrGeneration after updated: ");
//		for (int vertex : currGeneration) {
//			System.out.print(vertex + " ");
//		}
//		System.out.print("\nNo more updates\n");
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
		System.out.printf("------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
	}
}