package qqserver.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

/**
 * @ CSCI690_Team_5
 *
 * The is a server that listens to port 9999 to connect with clients and maintain communication.
 *
 *
 */

public class QQServer {

	private ServerSocket ss = null;



	// Create a set to store multiple users, if these user login, which is deemed valid users.
	private static ConcurrentHashMap<String, User> validusers = new ConcurrentHashMap<>();

	// Both ConcurrentHashMap and HashMap can be used to store valid users, since HashMap is not able to handle thread security,
	// while the former one can handle concurrent situation that avoid thread security problem,
	// so we use ConcurrentHashMap for multiple threads situation to guarantee security.


	private static ConcurrentHashMap<String, ArrayList<Message>> offLineMessage = new ConcurrentHashMap<>();


	static { // Initialize valid users in the static code.

		validusers.put("100", new User("100", "123456"));
		validusers.put("200", new User("200", "123456"));
		validusers.put("300", new User("300", "123456"));
		validusers.put("monkey", new User("monkey", "123456"));
		validusers.put("bear", new User("bear", "123456"));
		validusers.put("apple", new User("apple", "123456"));
		validusers.put("nyit690", new User("nyit690", "123456"));
		// Add offline
		ArrayList<Message> arrayList = new ArrayList<>();

		offLineMessage.put("100", arrayList);
		offLineMessage.put("200", arrayList);
		offLineMessage.put("300", arrayList);
		offLineMessage.put("monkey", arrayList);
		offLineMessage.put("bear", arrayList);
		offLineMessage.put("apple", arrayList);
		offLineMessage.put("nyit690", arrayList);


	}
	public static ConcurrentHashMap<String, ArrayList<Message>> getOffLineMessage() {

		return offLineMessage;
	}

	public void offLineMessageReload (String userId){
		ArrayList<Message> offLine = offLineMessage.get(userId);
		Iterator<Message> iterator = offLine.iterator();
		while (iterator.hasNext()) {
			Message message = iterator.next();
			try {
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageClientThreads.getServerConnectClientThread(userId).getSocket().getOutputStream());
				objectOutputStream.writeObject(message);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		// finish reading.
		offLine.clear();


	}

	// Method to verify if a user is valid.
	private boolean checkUser(String userId, String passwd) {

		User user = validusers.get(userId);
		if (user == null) { // userId is not existing in the set.

			return false;

		}
		if(!user.getPasswd().equals(passwd)) { // userId is valid but password is incorrect.
			return false;
		}

		return true;

	}




	public QQServer() {

		// Note: port can be written in a configuration file.

		try {
			System.out.println("Server is listning to port 9999...");

			// Start the thread of news broadcasting from server.

			new Thread(new SendNewsToAllService()).start();

			ss = new ServerSocket(9999);

			while(true) { // the server's listening will be lasting after established connection with a client.
				Socket socket = ss.accept(); // If there is no client connecting, the server will be in blocked status until next client connection.


				// Get input stream of a object that corresponds to the socket.
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

				// Get output stream of object corresponds to the socket.
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());


				User u = (User) ois.readObject(); // Read the object of user sent from client.
				// Create a message object for replying  client.
				Message message = new Message();


				// Verification.
				if(checkUser(u.getUserId(), u.getPasswd())) { // Login succeeds.
					message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
					// Reply the message object.
					oos.writeObject(message);
					// Create a thread to communicate with client, and the thread holds the socket object.
					ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, u.getUserId());
					// Start the thread.
					serverConnectClientThread.start();
					// Put the thread into a set to manage.
					ManageClientThreads.addClientThread(u.getUserId(),serverConnectClientThread);
					// Add offline.
					offLineMessageReload(u.getUserId());

				} else { // Login fails.
					System.out.println("User id = " + u.getUserId() + " pwd = " + u.getPasswd() + " Verification falis" );
					message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
					oos.writeObject(message);
					// Close socket.
					socket.close();
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// If server exits while loop, which means server is not listening, so ServerSocket should be closed.
			try {
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
	}


}
