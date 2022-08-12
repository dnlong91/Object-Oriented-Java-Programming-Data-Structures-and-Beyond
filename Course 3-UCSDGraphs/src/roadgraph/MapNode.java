/**
 * @author Ginny Dang
 * 
 * A class which reprsents a node of the graph
 *
 */

package roadgraph;

import java.util.*;
import geography.GeographicPoint;

public class MapNode implements Comparable<Object> {
	private GeographicPoint location; // lon & lat of this node
	private HashSet<MapEdge> edges; // all edges starting from this node
	private double distance; // the predicted distance of this node
	private double actualDistance; // the actual distance of this node from a start node
	
	public MapNode(GeographicPoint location) {
		this.location = location;
		this.edges = new HashSet<MapEdge>();
		this.distance = 0.0;
		this.actualDistance = 0.0;
	}
	
	public void addEdge(MapEdge newEdge) {
		edges.add(newEdge);
	}

	public int getNumEdges() {
		return edges.size();
	}
	
	public Set<MapEdge> getEdges() {
		return edges;
	}
	
	public Set<MapNode> getNeighbors() {
		Set<MapNode> neighbors = new HashSet<MapNode>();
		for (MapEdge edge : edges) {
			neighbors.add(edge.getEndNode());
		}
		return neighbors;
	}
	
	public GeographicPoint getPoint() {
		return this.location;
	}
	
	// Get the predicted distance
	public double getDistance() {
		return this.distance;
	}
	
	// Set the predicted distance
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	// Get the actual distance
	public double getActualDistance() {
		return this.actualDistance;
	}
	
	// Set the actual distance
	public void setActualDistance(double actualDistance) {
		this.actualDistance = actualDistance;
	}
	
	// Implement Comparable
	public int compareTo(Object o) {
		MapNode m = (MapNode)o; // convert to map node, may throw exception
		return ((Double)this.getDistance()).compareTo((Double) m.getDistance());
	}
	
	/** Returns whether two nodes are equal.
	 * Nodes are considered equal if their locations are the same, even if their street list is different.
	 */
	public boolean equals(Object o) {
		if (!(o instanceof MapNode) || (o == null)) {
			return false;
		}
		MapNode node = (MapNode)o;
		return node.location.equals(this.location);
	}
	
	/** Because we compare nodes using their location, we also 
	 * may use their location for HashCode.
	 * @return The HashCode for this node, which is the HashCode for the 
	 * underlying point
	 */
	public int HashCode() {
		return location.hashCode();
	}
	
	/** ToString to print out a MapNode method
	 *  @return the string representation of a MapNode
	 */
	public String toString() {
		String toReturn = "[NODE at location (" + location + ")";
		toReturn += " intersects streets: ";
		for (MapEdge e: edges) {
			toReturn += e.getRoadName() + ", ";
		}
		toReturn += "]";
		return toReturn;
	}
	
	public String roadNamesAsString() {
		String toReturn = "(";
		for (MapEdge e: edges) {
			toReturn += e.getRoadName() + ", ";
		}
		toReturn += ")";
		return toReturn;
	}
}
