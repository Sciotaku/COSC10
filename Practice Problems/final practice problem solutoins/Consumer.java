/**
 * Solutions to problem 2 of cs10 final practice exam
 * Takes messages from the box for ProducerConsumer
 *
 * @author warrenshepard, Spring 2023
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Spring 2014 to separate out helper classes
 */
public class Consumer extends Thread {
	private MessageBox box;
	private int money;
	private String name;
	public Consumer(String name, MessageBox box, int money) {
		this.name = name;
		this.box = box;
		this.money = money;
	}

	/**
	 * Takes messages from the box and prints them, until receiving EOF
	 */
	public void run() {
		try {
			String msg;
			while (!(msg = box.take()).equals("EOF") && money >= 5) {
				sleep(5000);
				money -= 5;
				System.out.println(name + " bought " + msg + "; Money remaining: $" + money);
			}
		}
		catch (InterruptedException e) {
			System.err.println(e);
		}
	}
}