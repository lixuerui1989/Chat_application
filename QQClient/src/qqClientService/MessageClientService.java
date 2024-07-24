package qqClientService;



import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

import qqcommon.Message;
import qqcommon.MessageType;

/**
 *
 * @ CSCI690_Team5
 *
 * For providing related methods of sending message to server.
 *
 */


public class MessageClientService {

	/**
	 *
	 * @param content: message content.
	 * @param senderId: sender id.
	 */

	public void sendMessageToAll(String content, String senderId) {



		// Construct message.
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_TO_ALL_MES); // Group chat message.
		message.setSender(senderId);

		message.setContent(content);
		message.setSendTime(new Date().toString());
		System.out.println(senderId + " sends a group message: " + content + " to all users ");

		// Send to server.
		try {
			ObjectOutputStream oos =
					new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
			oos.writeObject(message);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}



	/**
	 *
	 * @param content: message content.
	 * @param senderId: sender's id.
	 * @param getterId: receiver's id.
	 */

	public void sendMessageToOne(String content, String senderId, String getterId) {

		// Construct message.
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_COMM_MES); // Normal chat message.
		message.setSender(senderId);
		message.setGetter(getterId);
		message.setContent(content);
		message.setSendTime(new Date().toString());
		System.out.println(senderId + " sends " + content + " to " + getterId);

		// Send to server.
		try {
			ObjectOutputStream oos =
					new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
			oos.writeObject(message);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
