package challenges.inheritanceConstructors1;

public class Main {
	public static void main(String[] args) {
		Student s = new Student();
	}
}

/* What would be the output of this code? and why?
 * 
 * First, we call the default constructor of the Student class.
 * Then, that default constructor immediately calls the one-argument constructor 
 * since the 1st line of code in there is this("Student");
 * Due to that, we're go inside that one-argument constructor, 
 * which calls the super class with super(n);
 * Now, the super class which is Person has been call, we're gonna go inside it and start executing
 * Still, we haven't printed anything yet, we've been getting read to call the constructor
 * 
 * Once inside the one-argument constructor of the Person class, 
 * the name field is first set to "Student" since this string has been passed in
 * Next, #1 gets printed out.
 * Now, we've done with the constructor of the Person class, we go back to where we came from 
 * which is inside the one-argument constructor of the Student class.
 * Here, we've done executing the line super(n), so we the next line is printing #3
 * After this, we've done with the one-argument constructor of the Student class.
 * We came here from the default constructor of the Student class (remember calling this("Student");)
 * so what we do next is that we go beck in there and execute the last line of code which is printing #2
 * 
 * Finally, #1 #3 #2 is printed in the console
 *  */