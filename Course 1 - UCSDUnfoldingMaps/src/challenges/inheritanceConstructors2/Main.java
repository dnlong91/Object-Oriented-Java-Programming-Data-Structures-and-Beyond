package inheritanceConstructors2;

public class Main {

	public static void main(String[] args) {
		Student s = new Student();
	}
}

/* What would be the output of this code? And why?
 * 
 * First thing first, we go inside the default constructor of the Student class.
 * However, before we can set the name to "Student", the compiler's gonna insert some code in there first.
 * If the first line of the constructor is not either a call to the superclass constructor 
 * or a call to a constructor within the class, 
 * the compiler is going to insert a line to the superclass constructor that takes no arguments for us.
 * The compiler is going to insert super(); which is a no-argument constructor of the super class in there.
 * Next,Jave goes inside the super class which is the Person class to look for a no-argument constructor
 * However, there's no such constructor in there. 
 * It turns out that Java will not insert one for us if we already had a constructor that takes an argument. 
 * So this is gonna cause a compile error.
 */