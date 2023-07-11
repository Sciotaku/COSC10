import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Simple server that waits for someone to connect on port 4242,
 * and then repeatedly asks for their name and greets them.
 * Connect either by "telnet localhost 4242" or by running HelloClient.java
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 */


public class HelloServer {
	private static final Map<String, String> dictionary = new HashMap<>();

	public static void main(String[] args) throws IOException {
		// Listen on a server socket for a connection
		System.out.println("Waiting for someone to connect");
		ServerSocket listen = new ServerSocket(4242);
		// When someone connects, create a specific socket for them
		Socket sock = listen.accept();
		System.out.println("Someone connected");

		// Now talk with them
		PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		out.println("This is a Dictionary Server. Use GET <word> to find meaning of a word. Use SET <word> <meaning> to set the meaning of the word");

		String line;
		while ((line = in.readLine()) != null) {
			System.out.println("Received: " + line);
			String[] tokens = line.split(" ");
			String command = tokens[0];

			if (command.equals("GET")) {
				String word = tokens[1];
				String meaning = dictionary.get(word);
				if (meaning != null) {
					out.println("Meaning of " + word + ": " + meaning);
				}
				else {
					out.println("Word not found in the dictionary");
				}
			}
			else if (command.equals("SET")) {
				String word = tokens[1];
				String meaning = "";
				if (tokens.length > 2) {
					meaning = tokens[2];
					for (int i = 3; i < tokens.length; i++) {
						meaning += " " + tokens[i];
					}
				}
				dictionary.put(word, meaning);
				out.println("Definition added for " + word);
			}
			else {
				out.println("Invalid command. Please use GET <word> or SET <word> <meaning>");
			}
		}
		System.out.println("Client hung up");

		// Clean up shop
		out.close();
		in.close();
		sock.close();
		listen.close();
	}
}
