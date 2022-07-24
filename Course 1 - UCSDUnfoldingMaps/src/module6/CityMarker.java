package module6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** Implements a visual marker for cities on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Ginny Dang
 */
public class CityMarker extends CommonMarker {
	public static int TRI_SIZE = 5;  // The size of the triangle marker
	
	public CityMarker(Location location) {
		super(location);
	}
	
	public CityMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
		// Cities have properties: "name" (city name), "country" (country name)
		// and "population" (population, in millions)
	}
	
	// pg is the graphics object on which you call the graphics
	// methods.  e.g. pg.fill(255, 0, 0) will set the color to red
	// x and y are the center of the object to draw. 
	// They will be used to calculate the coordinates to pass
	// into any shape drawing methods.  
	// e.g. pg.rect(x, y, 10, 10) will draw a 10x10 square
	// whose upper left corner is at position x, y
	/**
	 * Implementation of method to draw marker on the map.
	 */
	public void drawMarker(PGraphics pg, float x, float y) {
		//System.out.println("Drawing a city");
		// Save previous drawing style
		pg.pushStyle();
		
		// IMPLEMENT: drawing triangle for each city
		pg.fill(150, 30, 30);
		pg.triangle(x, y-TRI_SIZE, x-TRI_SIZE, y+TRI_SIZE, x+TRI_SIZE, y+TRI_SIZE);
		
		// Restore previous drawing style
		pg.popStyle();
	}
	
	/** Show the title of the city if this marker is selected */
	public void showTitle(PGraphics pg, float x, float y) {
		String name = getCity() + " " + getCountry() + " ";
		String pop = "Pop: " + getPopulation() + " Million";
		
		pg.pushStyle();
		
		pg.fill(255, 255, 255);
		pg.textSize(12);
		pg.rectMode(PConstants.CORNER);
		pg.rect(x, y-TRI_SIZE-39, Math.max(pg.textWidth(name), pg.textWidth(pop)) + 6, 39);
		pg.fill(0, 0, 0);
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.text(name, x+3, y-TRI_SIZE-33);
		pg.text(pop, x+3, y - TRI_SIZE -18);
		
		pg.popStyle();
	}
	
	public void showNearby(PGraphics pg) {
		ArrayList<EarthquakeMarker> nearbyEarthquakes = getNearbyEarthquakes();
		// Sort by magnitude
		Collections.sort(nearbyEarthquakes);
		
		pg.pushStyle();
		// Box
		pg.fill(255, 250, 240);
		pg.rect(750, 300, 430, 35 + nearbyEarthquakes.size() * 15 + 28);
		// Box title
		pg.fill(0);
		pg.textAlign(PConstants.LEFT, PConstants.CENTER);
		pg.textSize(12);
		pg.text("Total Nearby earthquakes: " + nearbyEarthquakes.size(), 770, 328);
		int initY = 335;
		for (EarthquakeMarker e : nearbyEarthquakes) {
			initY += 15;
			String title = e.getTitle();
			pg.text(title, 780, initY);
		}
		pg.popStyle();
	}
	
	public String getCity() {
		return getStringProperty("name");
	}
	
	public String getCountry() {
		return getStringProperty("country");
	}
	
	private float getPopulation() {
		return Float.parseFloat(getStringProperty("population"));
	}
	
	private ArrayList<EarthquakeMarker> getNearbyEarthquakes() {
		ArrayList<EarthquakeMarker> nearbyEarthquakes = new ArrayList<EarthquakeMarker>();
		List<Marker> earthquakes = EarthquakeCityMap.getQuakes();
		for (Marker m: earthquakes) {
			EarthquakeMarker em = (EarthquakeMarker)m;
			double dist = em.getDistanceTo(this.getLocation());
			double threatRadius = em.threatCircle();
			if (dist <= threatRadius) {
				nearbyEarthquakes.add(em);
			}
		}
		return nearbyEarthquakes;
	}
}