package demos;

public class Week2Quiz {
	// Question 1
	/* The == operator compares object references. 
	 * Since two objects are created (one interned and one other String), 
	 * their references are not the same. 
	 * To compare the actual characters in the Strings, you need to use the .equals() method.
	 * */
	public static void tryEqualEqual() {
		String s1 = new String("String 1");
		String s2 = "String 1";
		if (s1 == s2) {
		    System.out.println("Equal");
		} else {
		    System.out.println("Not equal");
		}
	}
	
	// Question 2
	public static void assignText1() {
		/* In the the first line, s1 references a String object containing the text "My String". 
		 * In the second line, the variable text is created and set to reference the same object.  
		 * */
		String s1 = "My String";
		String text = s1;
		System.out.println(text);
	}
	
	public static void assignText2() {
		/* This will append "My " with "String" to create a new String, 
		 * which is stored back in the variable text.  
		 * */
		String text = "My ";
		String s2 = "String";
		text = text + s2;
		System.out.println(text);
	}
	
	public static void assignText3() {
		/* Strings are immutable. 
		 * So the call to text.concat returns a new string (which is ignored), but does not modify text.  
		 * */
		String text = "My ";
		text.concat("String");
		System.out.println(text);
	}
	
	// Question 4: Expected result: ["%", "%%", "%%%", "%%%%"]
	public static void trySplit1() {
		/* This will do the opposite of what we want, 
		 * splitting on the sequences of percent signs and leaving the text (and empty strings). 
		 * */
		String s = "%one%%two%%%three%%%%";
		String[] res = s.split("%+");
		
		for (int i = 0; i < res.length; i++) {
			System.out.print(res[i] + " ");
		}
		System.out.println();
	}
	
	public static void trySplit2() {
		/* This will work because it will split the string on any sequence of one or more lowercase characters. 
		 * */
		String s = "%one%%two%%%three%%%%";
		String[] res = s.split("[a-z]+");
		
		for (int i = 0; i < res.length; i++) {
			System.out.print(res[i] + " ");
		}
		System.out.println();
	}
	
	public static void trySplit3() {
		/* This will work, but it is not as general as the other solution in this list. 
		 * */
		String s = "%one%%two%%%three%%%%";
		String[] res = s.split("one|two|three");
		
		for (int i = 0; i < res.length; i++) {
			System.out.print(res[i] + " ");
		}
		System.out.println();
	}
	
	public static void trySplit4() {
		/* This will split the string on any one of the literal characters of [onetwhr,]. 
		 * So the array will have the "%", "%%", "%%%", and "%%%%" as desired, 
		 * but it will also have a bunch of empty strings.
		 * */
		String s = "%one%%two%%%three%%%%";
		String[] res = s.split("[one,two,three]");
		
		for (int i = 0; i < res.length; i++) {
			System.out.print(res[i] + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		// Question 1
//		tryEqualEqual();
		
		// Question 2
//		assignText1();
//		assignText2();
//		assignText3();
		
		// Question 4
		trySplit1();
		trySplit2();
		trySplit3();
		trySplit4();
	}
}