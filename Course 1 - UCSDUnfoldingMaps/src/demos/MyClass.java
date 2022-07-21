// This code is for testing the results of Quiz: Objects, memory models, and scope

package demos;

///////Question 1///////
//public class MyClass {
//	private int a;
//	private double b;
//	
//	public MyClass(int first, double second) {
//		this.a = first;
//		this.b = second;
//	}
//	
//	public static void main(String[] args) {
//		MyClass c1 = new MyClass(10, 20.5);
//		MyClass c2 = new MyClass(10, 31.5);
//		System.out.println(c1.a + ", " + c1.b);
//	}
//}


///////Question 2///////
//public class MyClass {
//	private int a;
//	private double b;
//	
//	public MyClass(int first, double second) {
//		this.a = first;
//		this.b = second;
//	}
//	
//	public static void main(String[] args) {
//		MyClass c1 = new MyClass(10, 20.5);
//		MyClass c2 = new MyClass(10, 31.5);
//		c2 = c1;
//		c1.a = 2;
//		System.out.println(c2.a);
//	}
//}

///////Question 4///////
//public class MyClass {
//	private int a;
//	private double b;
//	
//	public MyClass(int first, double second) {
//		this.a = first;
//		this.b = second;
//	}
//	
//	public static void incrementBoth(MyClass c1) {
//		c1.a = c1.a + 1;
//		c1.b = c1.b + 1.0;
//	}
//	
//	public static void main(String[] args) {
//		MyClass c1 = new MyClass(10, 20.5);
//		MyClass c2 = new MyClass(10, 31.5);
//		incrementBoth(c2);
//		System.out.println(c1.a + ", " + c2.a);
//	}
//}

///////Question 5///////
public class MyClass {
	private int a;
	private double b;
	
	public MyClass(int first, double second) {
		this.a = first;
		this.b = second;
	}
	
	public static void incrementBoth(MyClass c1) {
		c1.a = c1.a + 1;
		c1.b = c1.b + 1.0;
	}
	
	public static void incrementA(int first) {
		first = first + 1;
	}
	
	public static void incrementB(double second) {
		second = second + 1;
	}
	
	public static void main(String[] args) {
		MyClass c1 = new MyClass(10, 20.5);
		MyClass c2 = new MyClass(10, 31.5);
		incrementA(c2.a);
		incrementB(c2.b);
		System.out.println(c2.a + ", " + c2.b);
	}
}