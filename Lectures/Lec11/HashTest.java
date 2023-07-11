package Lec11;
public class HashTest {
	
	public static void main(String[] args) {
		char a = 'a';
		int b = (int)a;
		System.out.println("Casting 'a' to int is: "+ b);
		Character z = 'a';
		System.out.println("hashCode for 'a' is: " + z.hashCode());
		String y = "Hello";
		System.out.println("hashCode for 'hello' is: " + y.hashCode());
		System.out.println();
		
		//create new Blob with overridden equals and hashCode functions
		BlobHash b1 = new BlobHash();
		b1.x = 5; b1.y = 5; b1.r = 5;  //update b1's location
		BlobHash b2 = new BlobHash(); //create new HashBlob
		System.out.println("b1 is at (x,y,r): " + b1.x + ", " + b1.y + ", " + b1.r);
		System.out.println("b2 is at (x,y,r): " + b2.x + ", " + b2.y + ", " + b2.r);
		System.out.println("hashCode b1: " + b1.hashCode() + " b2:" + b2.hashCode());
		System.out.println("b1 is equal to b2: " + b1.equals(b2));
		b2.x = 5; b2.y = 5; b2.r = 5; //set b2 to same location as b1
		System.out.println("after update b1 now equals b2: " + b1.equals(b2));
		System.out.println("hashCode b1: " + b1.hashCode() + " b2:" + b2.hashCode());
	}
}

