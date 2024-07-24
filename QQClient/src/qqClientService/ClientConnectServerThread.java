package qqClientService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import qqcommon.Message;
import qqcommon.MessageType;

public class ClientConnectServerThread extends Thread {

	// The thread need to hold a socket.

	private Socket socket;

	// Constructor can get a object of Socket.
	public ClientConnectServerThread(Socket socket) {
		this.socket = socket;

	}

	//
	@Override
	public void run() {
		// Since thread need to communicate with server at background, a while loop is needed.
		while(true) {

			try {
				System.out.println("Thread of client is waiting to read message from server.");
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				Message message = (Message) ois.readObject(); // If server doesn't send message, the thread will be blocked.

				// Determine the type of the message, and conduct corresponding operation.

				// If read the list of online users returned from server, conduct to take and display the online users list info. and dispaly
				if(message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIED)) {
					// Take and display the online users list info..
					String[] onlineusers = message.getContent().split(" ");
					System.out.println("\n====== The online users list ======");
					for(int i = 0; i < onlineusers.length; i++) {
						System.out.println("User: " + onlineusers[i]);

					}

				} else if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) { // Normal one-to one chat message

					// Display the message replied by server.
					System.out.println("\n" + message.getSender() + " sends to you a message: " + message.getContent());

				}
				else if(message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)){
//                    // ManageClientConnectServerThread.removeThreadSource(ms.getGetter());
//                    // socket.close();
					break;
				}
				else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {

					// May add function to allow user to indicate a file saved path in the next version.

					// Display on the console of the client.
					System.out.println("\n" + message.getSender() + " sends to all with a message: " + message.getContent());



				} else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) { // File message type.

					System.out.println("\n" + message.getSender() + " sends to " + message.getGetter() + " a file from " + message.getSrc() + " to my computer at path of " + message.getDest());

					// Get out the file byte array from the message and write to a disk by file output stream.
					FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
					fileOutputStream.write(message.getFileBytes());

					fileOutputStream.close();
					System.out.println(" Saved the file successfully.");


				}


				else {
					System.out.println("Other types of message, do nothing.");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public Socket getSocket() {
		return socket;
	}




}
