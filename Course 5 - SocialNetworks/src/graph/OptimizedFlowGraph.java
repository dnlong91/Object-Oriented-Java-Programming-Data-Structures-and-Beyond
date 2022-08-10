package graph;

import java.util.*;

import util.FlowGraphLoader;

public class OptimizedFlowGraph extends FlowGraph{
	private HashSet<Integer> currGeneration;
	
	public OptimizedFlowGraph(HashSet<Integer> init, int rewardA, int rewardB) {
		super(init, rewardA, rewardB);
		currGeneration = new HashSet<Integer>();
		currGeneration.addAll(init);
	}
	
	public void kahnUpdateChanges() {
		// Print all members of currGeneration before updated
		System.out.print("\ncurrGeneration before updated: ");
		for (int vertex : currGeneration) {
			System.out.print(vertex + " ");
		}
		Queue<Integer> hasChanged = new LinkedList<Integer>();
		hasChanged.addAll(currGeneration);
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
		System.out.print("\ncurrGeneration after updated: ");
		for (int vertex : currGeneration) {
			System.out.print(vertex + " ");
		}
		System.out.print("\nNo more updates\n");
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

	public static void main(String[] args) {
		// Init the graph
		System.out.println("TEST:");
//		Integer arr1[] = { 5, 6, 14, 1, 3, 8 };
		Integer arr1[] = { 14, 1, 9 };
		HashSet<Integer> init = new HashSet<Integer>(Arrays.asList(arr1));
		OptimizedFlowGraph ofg = new OptimizedFlowGraph(init, 2, 1);
		// Load the graph
		System.out.print("Making a new optimized flow graph...");
		System.out.print("DONE. \nLoading the map...");
		FlowGraphLoader.loadFlowGraph(ofg, "data/small_test_graph.txt");
		System.out.println("DONE.");
		// Calculate new generations
		ofg.kahnUpdateChanges();
		ofg.printEffectTable();
	}
}