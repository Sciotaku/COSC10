import java.util.ArrayList;
import java.util.List;

/**
 * Solutions to problem 2 of cs10 final practice exam
 * Coordinates sending and receiving a message for ProducerConsumer
 *
 * @author warrenshepard, Spring 2023
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Spring 2014 to separate out helper classes
 */
public class MessageBox {
	public ArrayList<String> messages = new ArrayList<>();

	/**
	 * Put m as message once it's okay to do so (current message has been taken)
	 */
	public synchronized void put(String msg) throws InterruptedException {
		//check to see if message is not null, might have been woken by put() notifyAll
		while (messages.size() >= 15) {
			wait();
		}
		messages.add(msg);
		notifyAll(); //wakes producers AND consumers
	}

	/**
	 * Takes message once it's there, leaving empty message
	 */
	public synchronized String take() throws InterruptedException {
		//check to see if message is null, might have been woken by take() notifyAll
		while (messages.size() <= 5) {
			wait();
		}
		String msg = messages.remove(messages.size() - 1);
		notifyAll();  //wakes producers AND consumers
		return msg;
	}
}