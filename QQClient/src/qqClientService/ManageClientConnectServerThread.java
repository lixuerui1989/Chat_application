package qqClientService;

import java.util.HashMap;

/**
 * @ CSCI690_Team_5
 *
 * For managing thread that connect from client to server.
 */

public class ManageClientConnectServerThread {

	// Place multiple threads into a HashMap.
	private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

	// Put a thread into a set.
	public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
		hm.put(userId, clientConnectServerThread);

	}
	// Can get a corresponding thread by userId.
	public static ClientConnectServerThread getClientConnectServerThread(String userId) {
		return hm.get(userId);
	}



}
