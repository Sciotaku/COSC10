import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Handles communication to/from the server for the editor
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Chris Bailey-Kellogg; overall structure substantially revised Winter 2014
 * @author Travis Peters, Dartmouth CS 10, Winter 2015; remove EditorCommunicatorStandalone (use echo server for testing)
 * @author Rahul Gupta, Alex Marcoux, Problem Set 6
 */
public class EditorCommunicator extends Thread {
	private PrintWriter out;		// to server
	private BufferedReader in;		// from server
	protected Editor editor;		// handling communication for

	/**
	 * Establishes connection and in/out pair
	 */
	public EditorCommunicator(String serverIP, Editor editor) {
		this.editor = editor;
		System.out.println("connecting to " + serverIP + "...");
		try {
			Socket sock = new Socket(serverIP, 4242);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			System.out.println("...connected");
		}
		catch (IOException e) {
			System.err.println("couldn't connect");
			System.exit(-1);
		}
	}

	/**
	 * Sends message to the server
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the server
	 */
	public void run() {
		try {
			// Couldn't figure out why the code below didn't work. Would love some feedbacks.
//			String line;
//			while ((line = in.readLine()) != null) {
//				editor.setSketch(Messages.Message(editor.getSketch(), line));
			String line;
			while ((line = in.readLine()) != null) {
				editor.setSketch(Messages.Message(editor.getSketch(), line));
				for(int a : editor.getSketch().getShapes().keySet()){
					System.out.println(editor.getSketch().getShapes().get(a));
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("server hung up");
		}
	}


	// Send editor requests to the server
	// TODO: YOUR CODE HERE
	public void sendMessage(String message) {
		send(message);
	}


	public void addShape(Shape s) {
		sendMessage("add " + s.toString());
	}

	public void moveShape(int id, int dx, int dy) {
		sendMessage("move " +id+" "+dx+" "+dy);
	}

	public void deleteShape(int id) {
		sendMessage("delete " + id);
	}

	public void recolorShape(int id, Color c) {
		sendMessage("recolor " + id + " "+ c.getRGB());
	}
}

