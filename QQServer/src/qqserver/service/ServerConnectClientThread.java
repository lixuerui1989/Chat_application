package qqserver.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import qqserver.service.ManageClientThreads;
import qqcommon.Message;
import qqcommon.MessageType;

/**
 * @ CSCI690_Team5
 * 
 * The object of the class is keep communication from server to a client.
 * 
 */

public class ServerConnectClientThread extends Thread {
	private Socket socket;
	private String userId; // The ID connect to server.
	
	
	
	public ServerConnectClientThread(Socket socket, String userId) {
		this.socket = socket;
		this.userId = userId;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	@Override
	public void run() { // The thread is in running status, which can send and receive message.
		while(true) {
			
			try {
				System.out.println("Server and client " + userId + " are in communication, read data...");
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				Message message = (Message) ois.readObject();
				
				// Conduct operations according to the type of message.
				if(message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIED)) {
					// Client requests a online users list.
					
					/**
					 * 
					 * The form of online users list:
					 * 
					 * 100 200 nyit690
					 * 
					 */
					
					System.out.println(message.getSender() + " requests online users list");
					String onlineUser = ManageClientThreads.getOnlineUser();
					// Return a message.
					// Create a Message object and return to client.
					Message message2 = new Message();
					message2.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIED);
					message2.setContent(onlineUser);
					message2.setGetter(message.getSender());
					// Return to client.
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(message2);
					
				} else if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
					
					// Get getter id  according to the message, and get the corresponding thread.
					ServerConnectClientThread serverConnectClientThread = 
							ManageClientThreads.getServerConnectClientThread(message.getGetter());
					// Get the ObjectOutputStream corresponding to the socket, and send message to the client.
					ObjectOutputStream oos = 
							new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
					oos.writeObject(message); // Relay, if the client is not online, the message can be stored in database for offline message.
					
				} else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
					
					// Need to iterate the HashMap set of managing threads to get their sockets, and reply the message to them.
					
				HashMap<String, ServerConnectClientThread>	hm = ManageClientThreads.getHm();
				Iterator<String> iterator = hm.keySet().iterator();
				while(iterator.hasNext()) {
					// Get online users' id.
					String onlineUserId = iterator.next().toString();
					if(!onlineUserId.equals(message.getSender())) { // Exclude the sender from sending list.
						// Reply the message.
						ObjectOutputStream oos = 
								new ObjectOutputStream(hm.get(onlineUserId).getSocket().getOutputStream());
						oos.writeObject(message);
						
					}
					
				}
				
				} else if(message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {
					
					// Get the thread according to getter id and reply the message to it.
					ServerConnectClientThread serverConnectClientThread = 
							ManageClientThreads.getServerConnectClientThread(message.getGetter());
					ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
					// Reply
					oos.writeObject(message);
					
				}
				
				else if(message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) { // Client exits.
					
					System.out.println(message.getSender() + " exits.");
					
				
					
					
					// Delete the thread corresponds to the client in the set.
					ManageClientThreads.removeServerConnectClientThread(message.getSender());
					socket.close(); // Terminate connection.
					// Terminate the thread.
					break;
					
				}
				else {
					System.out.println("Other type, do nothing");
				}
			
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	

}
