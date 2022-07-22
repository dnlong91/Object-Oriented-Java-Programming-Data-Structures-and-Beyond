package polymorphism1;

public class Person {
	private String name; 
	
	public Person(String name)  { this.name = name; }
	
	public boolean isAsleep(int hr)  { return 22 < hr || 7 > hr; }
	
	public String toString()      { return name; }
	
	public void status( int hr ) {
		if ( this.isAsleep( hr ) )
			System.out.println( "Now offline: " + this );
		else
			System.out.println( "Now online: " + this );
		}
}