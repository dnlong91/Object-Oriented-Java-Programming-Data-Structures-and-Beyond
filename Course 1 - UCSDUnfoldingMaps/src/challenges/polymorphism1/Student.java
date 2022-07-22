package polymorphism1;

public class Student extends Person {
	public Student(String name)  {
		super(name);
	}
	
	 // override 
	public boolean isAsleep( int hr ) {
		return 2 < hr && 8 > hr; 
	}
}