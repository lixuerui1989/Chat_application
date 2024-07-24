package qqClientService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

/**
 * @ CSCI690_Team_5
 * User register and login.
 */
public class UserClientService {

	private User u = new User();
	private Socket socket;

	// Verify if the user is valid from server based on userID and pwd.
	public boolean checkUser(String userId, String pwd) {

		boolean b = false;

		// Create a user object.
		u.setUserId(userId);
		u.setPasswd(pwd);

		// Connect to the server, send object u.
		try {
			socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
			// Get the object of ObjectOutputSteam.
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(u); // Send user object.

			// Read message object from server
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Message ms = (Message) ois.readObject();

			if(ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {


				// To create a thread to keep communicating with server,
				// a new class ClientConnectServerThread should be created.
				ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
				// Start the thread of the server.
				clientConnectServerThread.start();
				// For scalability in the future, we put the thread into a set.
				ManageClientConnectServerThread.addClientConnectServerThread(userId, clientConnectServerThread);
				b = true;

			}else {
				// If login fails, there is not going to create a thread from client to server, so the socket should be closed.
				socket.close();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b;

	}

	// Request to server for online users list.

	public void onlineFriendList() {
		// Send a message of MESSAGE_GET_ONLINE_FRIED
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIED);
		message.setSender(u.getUserId());

		// Send to server.
		// Should get the ObjectOutputStream corresponds to the socket of the thread.
		try {
			// Get the thread object from the set that manage threads by userId.
			ClientConnectServerThread clientConnectServerThread = ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId());
			// Get the socket corresponds to the thread object.
			Socket socket = clientConnectServerThread.getSocket();
			// Get the ObjectOutputStream by the socket.
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(message); // Send a message object to server to request online users list.

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	// Write a method to exit system (process), and send a message of exiting system to server.
	public void logout() {
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
		message.setSender(u.getUserId()); // Must specify which client id.

		// Send message.
		try {
			//ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
			oos.writeObject(message);
			System.out.println(u.getUserId() + " logout system.");

			System.exit(0); // terminate the process.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
