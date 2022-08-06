package challenges.inheritanceConstructors1;

public class Student extends Person{
	public Student() {
		this("Student");
		System.out.print("#2 ");
	}
	
	public Student(String n) {
		super(n);
		System.out.print("#3 ");
	}
}