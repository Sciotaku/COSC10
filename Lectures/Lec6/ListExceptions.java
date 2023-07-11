package Lec6;
/**
 * Illustrations of handling exceptions, with lists
 */
public class ListExceptions {
	public static void main(String[] args) { // note: no "throws exception", as every method that could is wrapped
		
		SimpleList<String> list = new SinglyLinked<String>();
		
		try {
			list.add(-1, "?");
			System.out.println("I never run!");
			System.out.println("Neither do I");
		}
		catch (Exception e) {
			System.out.println("caught it!"); // will print -- we know this is bogus
		}

		try {
			list.add(-1, "?");
			System.out.println("Do I run?");
			System.out.println("No I don't");
		}
		catch (Exception e) {
			System.out.println("caught it again!"); // will print -- we know this is bogus
			System.out.println(e); 					//will give us the error message
		}
		finally {
			System.out.println("finally 1"); // executed whether or not caught an error
		}
	
		try {
			list.add(0, "?");
			System.out.println(list);
		}
		catch (Exception e) {
			System.out.println("why did I catch it again!"); // won't print -- we know this code is fine
		}
		finally {
			System.out.println("finally 2"); // executed whether or not caught an error
		}
}
}
