package qqserver.service;

import java.util.HashMap;
import java.util.Iterator;

/**
 *  @ CSCI690_Team5
 *
 *  The class is used to manage threads that communicate with server.
 *
 *
 */

public class ManageClientThreads {
	private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();


	// Return HashMap.
	public static HashMap<String, ServerConnectClientThread> getHm(){
		return hm;
	}

	// A method to add threads to the HashMap set.
	public static void addClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {

		hm.put(userId, serverConnectClientThread);

	}

	// Return a ServerConnectClientThread according to userId.
	public static ServerConnectClientThread getServerConnectClientThread(String userId) {
		return hm.get(userId);
	}

	// Write a method to remove a thread object in the set.
	public static void removeServerConnectClientThread(String userId) {

		hm.remove(userId);

	}


	// Write a method to return online users list.
	public static String getOnlineUser() {
		// Iteration of a set (use the key of HashMap).
		Iterator<String> iterator = hm.keySet().iterator();
		String onlineUserList = "";
		while(iterator.hasNext()) {
			onlineUserList += iterator.next().toString() + " ";

		}
		return onlineUserList;

	}



}
