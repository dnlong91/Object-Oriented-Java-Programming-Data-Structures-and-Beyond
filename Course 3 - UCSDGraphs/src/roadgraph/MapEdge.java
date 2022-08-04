/**
 * @author Ginny Dang
 * 
 * A class which reprsents an edge of the graph
 *
 */

package roadgraph;

import geography.GeographicPoint;

public class MapEdge {
	private MapNode start;
	private MapNode end;
	private String streetName;
	private String streetType;
	private double length;
	static final double DEFAULT_LENGTH = 0.01;
	
	public MapEdge(MapNode from, MapNode to, String roadName) {
		this(from, to, roadName, "", DEFAULT_LENGTH);
	}
	
	public MapEdge(MapNode from, MapNode to, String roadName, String roadType) {
		this(from, to, roadName, roadType, DEFAULT_LENGTH);
	}
	
	public MapEdge(MapNode from, MapNode to, String roadName, String roadType, double distance) {
		start = from;
		end = to;
		streetName = roadName;
		streetType = roadType;
		length = distance;
	}
	
	public GeographicPoint getStartPoint() {
		return this.start.getPoint();
	}
	
	public GeographicPoint getEndPoint() {
		return this.end.getPoint();
	}
	
	public MapNode getStartNode() {
		return this.start;
	}
	
	public MapNode getEndNode() {
		return this.end;
	}

	public double getLength() {
		return this.length;
	}

	public String getRoadName() {
		return this.streetName;
	}
	
	public String getRoadType() {
		return this.streetType;
	}
}