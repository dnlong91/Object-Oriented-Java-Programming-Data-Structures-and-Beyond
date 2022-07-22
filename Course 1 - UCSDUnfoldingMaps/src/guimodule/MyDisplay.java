package guimodule;

import processing.core.PApplet;

public class MyDisplay extends PApplet {
	public void setup() {
		size(400, 400);
		background(255, 180, 180);
	}
	
	public void draw() {
		// Yellow circle
		fill(255, 255, 0);
		ellipse(200, 200, 390, 390);
		// 2 black eyes
		fill(0, 0, 0);
		ellipse(120, 130, 50, 70);
		ellipse(280, 130, 50, 70);
		// A mouth
		//oFill();
		arc(200, 280, 100, 100, 0, PI);
	}
}