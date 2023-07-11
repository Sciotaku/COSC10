/**
 * Solutions to problem 2 of cs10 final practice exam
 * Puts messages in the box for ProducerConsumer
 *
 * @author warrenshepard, Spring 2023
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Spring 2014 to separate out helper classes
 * @author Tim Pierson, Dartmouth CS 10, Spring 2019, updated to accept number of messages to send in constructor
 */
public class Producer extends Thread {
	private MessageBox box;
	private int numberToSend;
	private String name;

	public Producer(String name, MessageBox box, int numberToSend) {
		this.box = box;
		this.numberToSend = numberToSend;
		this.name = name;
	}

	/**
	 * Wait for a while then puts a message
	 * Puts "EOF" when # messages have been put
	 */
	public void run() {
		try {
			for (int i = 0; i < numberToSend; i++) { 
				sleep((int)(Math.random()*5000)); //sleep for random time up to 5 seconds
				box.put("message #" + i); //put a new message in MessageBox
				System.out.println(name + " put message #" + i + " into the message box");
			}
			box.put("EOF"); //EOF means end of file
		}
		catch (InterruptedException e) {
			System.err.println(e);
		}
	}
}

