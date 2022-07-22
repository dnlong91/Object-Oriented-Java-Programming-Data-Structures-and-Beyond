package polymorphism1;

public class Main {

	public static void main(String[] args) {
		Person p;
		p = new Student("Sally");
		p.status(1);
	}
}

/* What is the output of this code? And why?
 * 
 * Even though p is a Person reference, the actual object it refers to is a Student.
 * So when we call p.status(1), we're calling that .status() on a Student object.
 * This is what Java does at run time.
 * Now, we're gonna look at the Student class for .status(). However, .status() is not in there,
 * it is actually inherited from the super class which is Person class because Student extends Person
 * Now, inside the Person class, we've found .status() and we can start tracing out code from here.
 * The first thing this method does is to check for a condition this.isAsleep( hr ).
 * But what does "this" mean here?
 * Although we're inside the Person class, "this" is at Runtime determined to be a Student object 
 * since the Student object is the one that called this method originally.
 * So we know that "this" refers to a Student object, we call the .isAsleep() method of the Student class 
 * and go back to that method inside the Student class to determine if a student is asleep at 1.
 * Once we've done that, the result turns out to be false. 
 * Therefore, back inside the Person class, this.isAsleep( hr ) inside the status() method is false, 
 * we execute the else statement instead and "Now online: Sally" gets printed out.
 * */
