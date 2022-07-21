package demos;

public class ArrayLocation {
	private double coords[];
	
	public ArrayLocation(double[] coords) {
		this.coords = coords;
	}

	public static void main(String[] args) {
		double[] coords = {5.0, 0.0};
		ArrayLocation accra = new ArrayLocation(coords);
		coords[0] = 32.9;
		coords[1] = -117.2;
		System.out.println(accra.coords[0]);
		/* Note that coords defined in this main function and coords constructed inside ArrayLocation both point to the same reference,
		 * when we change coords[0] and coords[1], accra.coords[0] and accra.coords[1] get change as well.
		*/
	}
}
