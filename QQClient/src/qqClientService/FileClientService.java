package qqClientService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;

import qqcommon.Message;
import qqcommon.MessageType;

/**
 * 
 * @ CSCI690_Team5
 * 
 * For file transferring service.
 * 
 */

public class FileClientService {
	
	public void sendFileToOne(String src, String dest, String senderId, String getterId) {
		
		// Read src file --> a message.
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_FILE_MES);
		message.setSender(senderId);
		message.setGetter(getterId);
		message.setSrc(src);
		message.setDest(dest);
		
		// Need to read the file.
		FileInputStream fileInputStream = null;
		byte[] fileBytes = new byte[(int)new File(src).length()];
		
		try {
			fileInputStream = new FileInputStream(src);
			fileInputStream.read(fileBytes); // Read src file into bytes array.
			// Set message for bytes array.
			message.setFileBytes(fileBytes);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		// Notice information.
		System.out.println("\n" + senderId + " sends to " + getterId + " a file: " + src + " file path of receiver" + dest);
		
		// Send.
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
