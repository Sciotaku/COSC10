package Lec6;
/**
 * Simple tests of list implementations
 */
public class ListTest {
	public static void main(String[] args) throws Exception {
		SimpleList<String> list = new SinglyLinked<String>();
		//list.get(0);
		System.out.println(list);
		list.add("1");
		System.out.println(list);
		list.add("2");
		System.out.println(list);
		list.add(0, "a");
		System.out.println(list);
		list.add(1, "c");
		System.out.println(list);
		list.add(1, "b");
		System.out.println(list);
		list.set(2, "e");
		System.out.println(list.get(2));
		list.add(0, "z");
		System.out.println(list);
		list.remove(2);
		System.out.println(list);
		list.remove(0);
		System.out.println(list);
		list.remove(1);
		System.out.println(list);		
	}
}
