package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development MOOC team
 * @author Ginny Dang
 * 
 */
public class AirportMap extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UnfoldingMap map;
	private List<Marker> airportList;
	private static List<Marker> routeList;
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	
	public void setup() {
		// setting up PApplet
		size(1440, 800);
		
		// setting up map and default events
//		map = new UnfoldingMap(this, 0, 0, 750, 550);
		map = new UnfoldingMap(this, 0, 0, 1440, 800);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
			
			//System.out.println(m.getProperties());
	
			m.setRadius(5);
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
		}
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			// get source and destination airportIds
			//System.out.println("Source: " + (String)route.getProperty("source") + " Dest: " + (String)route.getProperty("destination"));
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
		
			//System.out.println(sl.getProperties());
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeList.add(sl);
		}
		
		// Test the format of airports and routes
//		for (int i = 0; i < 5; i++) {
//			System.out.println(airportList.get(i).getProperties());
//			System.out.println(routeList.get(i).getProperties());
//		}
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		map.addMarkers(routeList);
		map.addMarkers(airportList);
	}
	
	@Override
	public void mouseMoved() {
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		}
		selectMarkerIfHover(airportList);
		//loop();
	}
	
	// If there is a marker selected 
	private void selectMarkerIfHover(List<Marker> markers) {
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}
		
		for (Marker m : markers) {
			CommonMarker marker = (CommonMarker)m;
			if (marker.isInside(map, mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				return;
			}
		}
	}
	
	/** The event handler for mouse clicks
	 * It will display an airport and all of its routes and other connected airports
	 */
	@Override
	public void mouseClicked() {
		if (lastClicked != null) {
			unhideMarkers();
			lastClicked.setClicked(false);
			lastClicked = null;
		} else if (lastClicked == null) {
			checkAirportsForClick();
		}
	}
	
	// Helper method that will check if an airport marker was clicked on
	// and respond appropriately
	private void checkAirportsForClick() {
		if (lastClicked != null) return;
		// Loop over the airport markers to see if one of them is selected
		for (Marker airport : airportList) {
			if (!airport.isHidden() && airport.isInside(map, mouseX, mouseY)) {
				lastClicked = (CommonMarker)airport;
				int airportId = Integer.parseInt((String)airport.getProperty("id"));
				HashMap<Integer, Integer> destinations = new HashMap<Integer, Integer>();
				destinations.put(airportId, airportId);
				// Hide all routes that are not connected to the clicked airport
				for (Marker route : routeList) {
					int source = Integer.parseInt((String)route.getProperty("source"));
					int dest = Integer.parseInt((String)route.getProperty("destination"));
					if (airportId != source && airportId != dest) {
						route.setHidden(true);
					} else {
						if (airportId == source) {
							if (!destinations.containsKey(dest)) {
								destinations.put(dest, airportId);
							}
						} else if (airportId == dest) {
							if (!destinations.containsKey(source)) {
								destinations.put(source, airportId);
							}
						}
					}
				}
				
				// Hide all airports that don't have route connected to the clicked airport
				for (Marker otherAirport : airportList) {
					int otherId = Integer.parseInt((String)otherAirport.getProperty("id"));
					if (!destinations.containsKey(otherId)) {
						otherAirport.setHidden(true);
					}
				}
				lastClicked.setClicked(true);
				return;
			}
		}
	}
	
	// loop over and unhide all markers
	private void unhideMarkers() {
		for(Marker marker : airportList) {
			marker.setHidden(false);
		}
		
		for(Marker marker : routeList) {
			marker.setHidden(false);
		}
	}
	
	public void draw() {
		background(0);
		map.draw();
	}
	
	public static List<Marker> getRoutes() {
		return routeList;
	}
}