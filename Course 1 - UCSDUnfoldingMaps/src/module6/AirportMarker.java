package module6;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(11);
		pg.ellipse(x, y, 5, 5);
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		String name = "Airport: " + ((String)getProperty("name")).replaceAll("\"", "") + ", ";
		String city = "City: " + ((String)getProperty("city")).replaceAll("\"", "") + ", ";
		String country = "Country: " + ((String)getProperty("country")).replaceAll("\"", "");
		float maxTextWidth = Math.max(pg.textWidth(city), pg.textWidth(country));
		maxTextWidth = Math.max(maxTextWidth, pg.textWidth(name));
		
		pg.pushStyle();
		
		pg.fill(255, 255, 255);
		pg.textSize(12);
		pg.rectMode(PConstants.CORNER);
		pg.rect(x, y - 44, maxTextWidth + 5, 60);
		pg.fill(0, 0, 0);
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.text(name, x + 3, y - 38);
		pg.text(city, x + 3, y - 23);
		pg.text(country, x + 3, y - 8);
		
		pg.popStyle();
	}

	@Override
	public void showNearby(PGraphics pg) {
		ArrayList<String> routes = getConnectedRoutes();
		String boxTitle = "Total Connected Routes: " + routes.size();
		
		pg.pushStyle();
		// Box
		pg.fill(255, 250, 240);
		pg.rect(0, 0, pg.textWidth(boxTitle) + 30, 35 + routes.size() * 15 + 28);
		// Box title
		pg.fill(0);
		pg.textAlign(PConstants.LEFT, PConstants.CENTER);
		pg.textSize(12);
		pg.text(boxTitle, 10, 28);
		int initX = 10;
		int initY = 35;
		for (String r : routes) {
			if (initY + 15 < 750) {
				initY += 15;
			} else {
				initX += pg.textWidth(r) + 15;
				initY = 50;
			}
			pg.text(r, initX, initY);
		}
		pg.popStyle();
	}
	
	private ArrayList<String> getConnectedRoutes() {
		ArrayList<String> connectedRoutes = new ArrayList<String>();
		List<Marker> routes = AirportMap.getRoutes();
		int airportId = Integer.parseInt((String)getProperty("id"));
		for (Marker r: routes) {
			int source = Integer.parseInt((String)r.getProperty("source"));
			int dest = Integer.parseInt((String)r.getProperty("destination"));
			if (airportId == source || airportId == dest) {
				String newRoute = r.getProperty("source-code") + " => " + r.getProperty("dest-code");
				connectedRoutes.add(newRoute);
			}
		}
		return connectedRoutes;
	}
}