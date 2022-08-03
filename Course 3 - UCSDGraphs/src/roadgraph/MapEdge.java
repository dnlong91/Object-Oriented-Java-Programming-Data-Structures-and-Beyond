package roadgraph;

import geography.GeographicPoint;

public class MapEdge {
	private GeographicPoint start;
	private GeographicPoint end;
	private String streetName;
	private String streetType;
	private double distance;
	
	public MapEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length) {
		start = from;
		end = to;
		streetName = roadName;
		streetType = roadType;
		distance = length;
	}
	
	public GeographicPoint getStart() {
		return this.start;
	}
	
	public GeographicPoint getEnd() {
		return this.end;
	}
}
