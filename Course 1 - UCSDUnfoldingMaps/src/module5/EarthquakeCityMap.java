package module5;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Ginny Dang
 * Date: July 23, 2022
 * */
public class EarthquakeCityMap extends PApplet {
	// We will use member variables, instead of local variables, to store the data
	// that the setup and draw methods will need to access (as well as other methods)
	// You will use many of these variables, but the only one you should need to add
	// code to modify is countryQuakes, where you will store the number of earthquakes
	// per country.
	
	// You can ignore this.  It's to get rid of eclipse warnings
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFILINE, change the value of this variable to true
	private static final boolean offline = false;
	
	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
	// The files containing city names and info and country names and info
	private String cityFile = "city-data.json";
	private String countryFile = "countries.geo.json";
	
	// The map
	private UnfoldingMap map;
	
	// Markers for each city
	private List<Marker> cityMarkers;
	// Markers for each earthquake
	private List<Marker> quakeMarkers;

	// A List of country markers
	private List<Marker> countryMarkers;
	
	// NEW IN MODULE 5
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	
	public void setup() {		
		// (1) Initializing canvas and map tiles
		//size(900, 700);
		size(950, 700);
		if (offline) {
		    map = new UnfoldingMap(this, 0, 0, 650, 600, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom";  // The same feed, but saved August 7, 2015
		} else {
			map = new UnfoldingMap(this, 0, 0, 650, 600, new Microsoft.HybridProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
		    //earthquakesURL = "2.5_week.atom";
		}
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// (2) Reading in earthquake data and geometric properties
	    //     STEP 1: load country features and markers
		List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		//     STEP 2: read in city data
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		cityMarkers = new ArrayList<Marker>();
		for(Feature city : cities) {
			cityMarkers.add(new CityMarker(city));
		}
	    
		//     STEP 3: read in earthquake RSS feed
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    quakeMarkers = new ArrayList<Marker>();
	    
	    for(PointFeature feature : earthquakes) {
	    	//check if LandQuake
		    if(isLand(feature)) {
		    	quakeMarkers.add(new LandQuakeMarker(feature));
		    }
		    // OceanQuakes
		    else {
		    	quakeMarkers.add(new OceanQuakeMarker(feature));
		    }
	    }

	    // could be used for debugging
	    printQuakes();
	 		
	    // (3) Add markers to map
	    //     NOTE: Country markers are not added to the map.  They are used
	    //           for their geometric properties
	    map.addMarkers(quakeMarkers);
	    map.addMarkers(cityMarkers);
	}  // End setup
	
	public void draw() {
		background(0);
		map.draw();
		addKey();
	}
	
	/** Event handler that gets called automatically when the 
	 * mouse moves.
	 */
	@Override
	public void mouseMoved() {
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		}
		selectMarkerIfHover(quakeMarkers);
		selectMarkerIfHover(cityMarkers);
	}
	
	// If there is a marker under the cursor, and lastSelected is null 
	// set the lastSelected to be the first marker found under the cursor
	// Make sure you do not select two markers.
	private void selectMarkerIfHover(List<Marker> markers) {
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}
		for (Marker m : markers) {
			CommonMarker marker = (CommonMarker) m;
			if (marker.isInside(map, mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				return;
			}
		}
	}
	
	/** The event handler for mouse clicks
	 * It will display an earthquake and its threat circle of cities
	 * Or if a city is clicked, it will display all the earthquakes 
	 * where the city is in the threat circle
	 */
	@Override
	public void mouseClicked() {
		// Hint: You probably want a helper method or two to keep this code
		// from getting too long/disorganized
		if (lastClicked != null) {
			unhideMarkers(); // Show all marker
			lastClicked = null; // De-select the last clicked marker
		} else {
			// determine which marker is being selected 
			checkEarthquakesForClick(); // See if the selected marker is amongst quakeMarkers
			if (lastClicked == null) {
				checkCitiesForClick(); // See if the selected marker is amongst cityMarkers if it's not a quake
			}
		}
	}
	
	private void checkEarthquakesForClick() {
		if (lastClicked != null) {
			return;
		}
		
		// Loop over the earthquake markers to see if one of them is selected
		for (Marker qm : quakeMarkers) {
			EarthquakeMarker em = (EarthquakeMarker)qm;
			if (!em.isHidden() && em.isInside(map, mouseX, mouseY)) {
				lastClicked = em;
				// Hide all the other earthquakes
				for (Marker qmhide : quakeMarkers) {
					if (qmhide != lastClicked) {
						qmhide.setHidden(true);
					}
				}
				// Hide all the other cities outside of the threat circle
				double threatRadius = em.threatCircle();
				for (Marker cmhide : cityMarkers) {
					double dist = cmhide.getDistanceTo(em.getLocation());
					if (dist > threatRadius) {
						cmhide.setHidden(true);
					}
				}
				return;
			}
		}
	}
	
	private void checkCitiesForClick() {
		if (lastClicked != null) {
			return;
		}
		
		// Loop over the city markers to see if one of them is selected
		for (Marker cm : cityMarkers) {
			if (!cm.isHidden() && cm.isInside(map, mouseX, mouseY)) {
				lastClicked = (CommonMarker) cm;
				// Hide all the other cities
				for (Marker cmhide : cityMarkers) {
					if (cmhide != lastClicked) {
						cmhide.setHidden(true);
					}
				}
				// Hide all the other earthquakes outside of the threat circle
				for (Marker qmhide : quakeMarkers) {
					EarthquakeMarker emhide = (EarthquakeMarker) qmhide;
					double threatRadius = emhide.threatCircle();
					double dist = emhide.getDistanceTo(cm.getLocation());
					if (dist > threatRadius) {
						emhide.setHidden(true);
					}
				}
				return;
			}
		}
	}
	
	// loop over and unhide all markers
	private void unhideMarkers() {
		for(Marker marker : quakeMarkers) {
			marker.setHidden(false);
		}
			
		for(Marker marker : cityMarkers) {
			marker.setHidden(false);
		}
	}
	
	// helper method to draw key in GUI
	private void addKey() {	
		// Remember you can use Processing's graphics methods here
		// Legend box
		fill(255, 250, 240);
		rect(750, 0, 160, 250);
		// Legend box title
		fill(0);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Earthquake Key", 770, 28);
		// City Marker symbol
		fill(color(235, 71, 71));
		triangle(780, 65, 785, 55, 790, 65);
		// Land Quake symbol
		fill(color(255, 255, 255));
		ellipse(785, 81, 11, 11);
		// Ocean Quake symbol
		fill(color(255, 255, 255));
		rect(780, 94, 11, 11);
		// Shallow color
		fill(color(254, 226, 197));
		ellipse(785, 155, 11, 11);
		// Intermediate color
		fill(color(0, 29, 110));
		ellipse(785, 175, 11, 11);
		// Deep color
		fill(color(224, 36, 1));
		ellipse(785, 195, 11, 11);
		// Past hour symbol
		fill(color(255, 255, 255));
		ellipse(785, 215, 11, 11);
		line(785 - 7, 215 - 7, 785 + 7, 215 + 7);
		line(785 + 7, 215 - 7, 785 - 7, 215 + 7);
		
		// Key titles
		fill(0, 0, 0);
		text("City Marker", 800, 60);
		text("Land Quake", 800, 80);
		text("Ocean Quake", 800, 100);
		text("Size ~ Magnitude", 770, 123);
		text("Shallow", 800, 155);
		text("Intermediate", 800, 175);
		text("Deep", 800, 195);
		text("Past hour", 800, 215);
	}
	
	// Checks whether this quake occurred on land.  If it did, it sets the 
	// "country" property of its PointFeature to the country where it occurred
	// and returns true.  Notice that the helper method isInCountry will
	// set this "country" property already.  Otherwise it returns false.	
	private boolean isLand(PointFeature earthquake) {
		// IMPLEMENT THIS: loop over all countries to check if location is in any of them
		// If it is, add 1 to the entry in countryQuakes corresponding to this country.
		for (Marker country : countryMarkers) {
			if (isInCountry(earthquake, country)) {
				return true;
			}
		}
		// not inside any country
		return false;
	}
	
	// prints countries with number of earthquakes
	private void printQuakes() {
		int totalWaterQuakes = quakeMarkers.size();
		for (Marker country : countryMarkers) {
			String countryName = country.getStringProperty("name");
			int numQuakes = 0;
			for (Marker marker : quakeMarkers) {
				EarthquakeMarker eqMarker = (EarthquakeMarker)marker;
				if (eqMarker.isOnLand()) {
					if (countryName.equals(eqMarker.getStringProperty("country"))) {
						numQuakes++;
					}
				}
			}
			if (numQuakes > 0) {
				totalWaterQuakes -= numQuakes;
				System.out.println(countryName + ": " + numQuakes);
			}
		}
		System.out.println("OCEAN QUAKES: " + totalWaterQuakes);
	}
	
	// helper method to test whether a given earthquake is in a given country
	// This will also add the country property to the properties of the earthquake feature if 
	// it's in one of the countries.
	// You should not have to modify this code
	private boolean isInCountry(PointFeature earthquake, Marker country) {
		// getting location of feature
		Location checkLoc = earthquake.getLocation();

		// some countries represented it as MultiMarker
		// looping over SimplePolygonMarkers which make them up to use isInsideByLoc
		if(country.getClass() == MultiMarker.class) {
			// looping over markers making up MultiMarker
			for(Marker marker : ((MultiMarker)country).getMarkers()) {
				// checking if inside
				if(((AbstractShapeMarker)marker).isInsideByLocation(checkLoc)) {
					earthquake.addProperty("country", country.getProperty("name"));
					// return if is inside one
					return true;
				}
			}
		}
		// check if inside country represented by SimplePolygonMarker
		else if(((AbstractShapeMarker)country).isInsideByLocation(checkLoc)) {
			earthquake.addProperty("country", country.getProperty("name"));
			return true;
		}
		return false;
	}
}