package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Ginny Dang
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	// Key colors
	private int lightBlue = color(187, 241, 250);
	private int avgBlue = color(81, 194, 213);
	private int darkBlue = color(0, 120, 170);
	private int orange = color(246, 107, 14);
	private int lightRed = color(235, 71, 71);
	private int darkRed = color(153, 0, 0);
	
	// Setup the map
	public void setup() {
		//size(950, 600, OPENGL);
		size(1200, 600);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
			earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Microsoft.HybridProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    //Add new SimplePointMarker's to the List markers (so that it will be added to the map)
	    for (PointFeature eq : earthquakes) {
	    	SimplePointMarker mk = createMarker(eq);
	    	markers.add(mk);
	    }
	    
	    // Add the markers to the map so that they are displayed
	    map.addMarkers(markers);
	}
		
	// Takes in an earthquake feature and returns a SimplePointMarker for that earthquake
	private SimplePointMarker createMarker(PointFeature feature)
	{  
		// To print all of the features in a PointFeature (so you can see what they are)
		// uncomment the line below.  Note this will only print if you call createMarker 
		// from setup
		//System.out.println(feature.getProperties());
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		// Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    //int yellow = color(255, 255, 0);
		
		// Style the marker's size and color according to the magnitude of the earthquake.  
	    if (mag <= 2.5) {
	    	marker.setColor(lightBlue);
	    	marker.setRadius(7.0f);
	    } else if (mag > 2.5 && mag <= 5.4) {
	    	marker.setColor(avgBlue);
	    	marker.setRadius(8.0f);
	    } else if (mag > 5.4 && mag <= 6.0) {
	    	marker.setColor(darkBlue);
	    	marker.setRadius(9.0f);
	    } else if (mag > 6.0 && mag <= 6.9) {
	    	marker.setColor(orange);
	    	marker.setRadius(10.0f);
	    } else if (mag > 6.9 && mag <= 7.9) {
	    	marker.setColor(lightRed);
	    	marker.setRadius(11.0f);
	    } else {
	    	marker.setColor(darkRed);
	    	marker.setRadius(12.0f);
	    }
	    
	    // Finally return the marker
	    return marker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}

	//Helper method to draw key in GUI
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		// Legend box
		fill(221, 221, 221);
        rect(950, 0, 150, 250);
        // Legend box title
        fill(0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("Earthquake Key", 980, 25);
        // Keys
        fill(darkRed);
        ellipse(980, 60, 13, 13);
        fill(0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("8.0 or greater", 1000, 57);
        
        fill(lightRed);
        ellipse(980, 90, 12, 12);
        fill(0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("7.0 to 7.9", 1000, 87);
        
        fill(orange);
        ellipse(980, 120, 11, 11);
        fill(0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("6.1 to 6.9", 1000, 117);
        
        fill(darkBlue);
        ellipse(980, 150, 10, 10);
        fill(0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("5.5 to 6.0", 1000, 147);
        
        fill(avgBlue);
        ellipse(980, 180, 9, 9);
        fill(0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("2.5 to 5.4", 1000, 177);
        
        fill(lightBlue);
        ellipse(980, 210, 8, 8);
        fill(0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("2.5 or less", 1000, 207);
	}
}