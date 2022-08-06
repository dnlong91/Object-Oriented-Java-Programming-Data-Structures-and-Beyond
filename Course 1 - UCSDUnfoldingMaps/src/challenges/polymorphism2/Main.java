package challenges.polymorphism2;

public class Main {

	public static void main(String[] args) {
		Person u = new Undergrad();
		u.method1();
	}
}

/* What is the output of this code? And why?
 * 
 * u is an Undergrad object so in runtime, that's what Java's going to look at.
 * Inside the Undergrad class, we don't see .method1(), but since Undergrad extends Student,
 * .method1() is inherited from the Student class.
 * Now, Java looks inside the Student class and finds method1() so we can start executing from there.
 * The first thing that gets printed out is "Student 1 "
 * Next, we execute the line super.method1(); but what exactly is the "super" here?
 * Since the super class of the Student class is the Person class, so Java's gonna bind that Person class into .method1() (Static Binding)
 * Now, we look inside the Person class to find method1() and execute it, so "Person 1 " gets printed out next
 * Now, we go back to where we called super.method1(); which is inside method1() of the Student class and keep executing
 * Here we find the call to method2(); but which method2() tho?
 * Since we don't have a calling object here, Java's gonna insert the keyword "this", 
 * therefore, we're calling method2() on the calling object and this is gonna bind at Runtime
 * And the actual type of the object at Runtime is Undergrad, which means now, 
 * we're gonna go back inside the Undergrad class to execute method2()
 * Here, "Undergrad 2 " gets printed out
 * 
 * So the final result is: Student 1 Person 1 Undergrad 2 
 * */
