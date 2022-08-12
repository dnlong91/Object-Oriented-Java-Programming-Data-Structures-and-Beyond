package module6;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** Implements a visual marker for earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Ginny Dang
 */
public abstract class EarthquakeMarker extends CommonMarker implements Comparable<EarthquakeMarker> {
	// Did the earthquake occur on land?  This will be set by the subclasses.
	protected boolean isOnLand;

	// The radius of the Earthquake marker
	// You will want to set this in the constructor, either
	// using the thresholds below, or a continuous function
	// based on magnitude. 
	protected float radius;
	
	// constants for distance
	protected static final float kmPerMile = 1.6f;
	
	/** Greater than or equal to this threshold is a moderate earthquake */
	public static final float THRESHOLD_MODERATE = 5;
	/** Greater than or equal to this threshold is a light earthquake */
	public static final float THRESHOLD_LIGHT = 4;

	/** Greater than or equal to this threshold is an intermediate depth */
	public static final float THRESHOLD_INTERMEDIATE = 70;
	/** Greater than or equal to this threshold is a deep depth */
	public static final float THRESHOLD_DEEP = 300;

	// constants for colors
	private Color shallow = new Color(254, 226, 197);
	private Color intermediate = new Color(0, 29, 110);
	private Color deep = new Color(224, 36, 1);
	
	// abstract method implemented in derived classes
	public abstract void drawEarthquake(PGraphics pg, float x, float y);
	
	// constructor
	public EarthquakeMarker (PointFeature feature) {
		super(feature.getLocation());
		// Add a radius property and then set the properties
		java.util.HashMap<String, Object> properties = feature.getProperties();
		float magnitude = Float.parseFloat(properties.get("magnitude").toString());
		properties.put("radius", 2*magnitude );
		setProperties(properties);
		this.radius = 1.75f*getMagnitude();
	}
	
	public int compareTo(EarthquakeMarker marker) {
		if (this.getMagnitude() < marker.getMagnitude()) { return - 1; }
		else if (this.getMagnitude() > marker.getMagnitude()) { return 1; }
		else { return 0; }
	}
	
	// calls abstract method drawEarthquake and then checks age and draws X if needed
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		// save previous styling
		pg.pushStyle();
			
		// determine color of marker from depth
		colorDetermine(pg);
		
		// call abstract method implemented in child class to draw marker shape
		drawEarthquake(pg, x, y);
		
		// IMPLEMENT: add X over marker if within past day		
		String age = getStringProperty("age");
		if ("Past Hour".equals(age) || "Past Day".equals(age)) {
			
			pg.strokeWeight(2);
			int buffer = 2;
			pg.line(x-(radius+buffer), 
					y-(radius+buffer), 
					x+radius+buffer, 
					y+radius+buffer);
			pg.line(x-(radius+buffer), 
					y+(radius+buffer), 
					x+radius+buffer, 
					y-(radius+buffer));
		}
		
		// reset to previous styling
		pg.popStyle();
	}

	/** Show the title of the earthquake if this marker is selected */
	public void showTitle(PGraphics pg, float x, float y) {
		String title = getTitle();
		pg.pushStyle();
		
		pg.rectMode(PConstants.CORNER);
		
		pg.stroke(110);
		pg.fill(255,255,255);
		pg.rect(x, y + 15, pg.textWidth(title) +6, 18, 5);
		
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.fill(0);
		pg.text(title, x + 3 , y +18);
		
		pg.popStyle();
	}
	
	public void showNearby(PGraphics pg) {
		ArrayList<CityMarker> nearbyCities = getNearbyCities();
		
		pg.pushStyle();
		// Box
		pg.fill(255, 250, 240);
		pg.rect(750, 300, 200, 35 + nearbyCities.size() * 15 + 28);
		// Box title
		pg.fill(0);
		pg.textAlign(PConstants.LEFT, PConstants.CENTER);
		pg.textSize(12);
		pg.text("Total affected cities: " + nearbyCities.size(), 770, 328);
		// List all the affected cities
		int initY = 335;
		for (CityMarker c : nearbyCities) {
			initY += 15;
			String title = c.getCity();
			String country = c.getCountry();
			pg.text(title + ", " + country, 780, initY);
		}
		pg.popStyle();
	}
	
	/**
	 * Return the "threat circle" radius, or distance up to 
	 * which this earthquake can affect things, for this earthquake.   
	 * DISCLAIMER: this formula is for illustration purposes
	 *  only and is not intended to be used for safety-critical 
	 *  or predictive applications.
	 */
	public double threatCircle() {	
		double miles = 20.0f * Math.pow(1.8, 2*getMagnitude()-5);
		double km = (miles * kmPerMile);
		return km;
	}
	
	// determine color of marker from depth
	// We use: Deep = red, intermediate = blue, shallow = yellow
	private void colorDetermine(PGraphics pg) {
		float depth = getDepth();
		
		if (depth < THRESHOLD_INTERMEDIATE) {
			pg.fill(shallow.getRGB());
		} else if (depth < THRESHOLD_DEEP) {
			pg.fill(intermediate.getRGB());
		} else {
			pg.fill(deep.getRGB());
		}
	}
	
	/** toString
	 * Returns an earthquake marker's string representation
	 * @return the string representation of an earthquake marker.
	 */
	public String toString() {
		return getTitle();
	}
	/*
	 * getters for earthquake properties
	 */
	
	public float getMagnitude() {
		return Float.parseFloat(getProperty("magnitude").toString());
	}
	
	public float getDepth() {
		return Float.parseFloat(getProperty("depth").toString());	
	}
	
	public String getTitle() {
		return (String) getProperty("title");	
	}
	
	public float getRadius() {
		return Float.parseFloat(getProperty("radius").toString());
	}
	
	public boolean isOnLand() {
		return isOnLand;
	}
	
	private ArrayList<CityMarker> getNearbyCities() {
		ArrayList<CityMarker> nearbyCities = new ArrayList<CityMarker>();
		List<Marker> cities = EarthquakeCityMap.getCities();
		for (Marker m : cities) {
			CityMarker cm = (CityMarker)m;
			double dist = cm.getDistanceTo(this.getLocation());
			double threatRadius = threatCircle();
			if (dist <= threatRadius) {
				nearbyCities.add(cm);
			}
		}
		return nearbyCities;
	}
}