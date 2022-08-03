package roadgraph;

import java.util.*;
import geography.GeographicPoint;

public class MapNode {
	private GeographicPoint location;
	private List<MapEdge> edges;
	
	public MapNode(GeographicPoint location) {
		this.location = location;
		this.edges = new ArrayList<MapEdge>();
	}
	
	public boolean addEdge(GeographicPoint endLocation, String roadName, String roadType, double length) {
		MapEdge newEdge = new MapEdge(location, endLocation, roadName, roadType, length);
		for (MapEdge edge : edges) {
			GeographicPoint currEnd = edge.getEnd();
			if (currEnd == endLocation) {
				return false;
			}
		}
		edges.add(newEdge);
		return true;
	}

	public int getNumEdges() {
		return edges.size();
	}

	public List<GeographicPoint> getNeighbors() {
		List<GeographicPoint> neighbors = new ArrayList<GeographicPoint>();
		for (MapEdge edge : edges) {
			neighbors.add(edge.getEnd());
		}
		return neighbors;
	}
	
	public GeographicPoint getPoint() {
		return this.location;
	}
}
