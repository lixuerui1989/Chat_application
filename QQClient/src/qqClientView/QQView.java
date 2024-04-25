package qqClientView;

import qqClientService.FileClientService;
import qqClientService.MessageClientService;
import qqClientService.UserClientService;
import qqClientUtils.Utility;

/**
  *  
  * @author CSCI690_Team_5
  * 
  * Menu interface of client.
  * 
  */

public class QQView {
	
	private boolean loop = true; // Control to display menu.
	private String key = ""; // Receive keyboard input from clients. 
	private UserClientService userClientService = new UserClientService();// The object is used to login server and register.
	private MessageClientService messageClientService = new MessageClientService(); // For one-to-one chat and group chat.
	private FileClientService fileClientService = new FileClientService(); // For transferring files.
	
	public static void main(String[] args) {
		new QQView().mainMenu();
		System.out.println("Client logout");
	}
	// Display main menu.
	private void mainMenu(){
		while(loop) {
			System.out.println("========== Welcome to login the network communication system ==========");
			System.out.println("\t\t 1 Login");
			System.out.println("\t\t 9 Logout");
			System.out.print("Please inout your selection: ");
			
			key = Utility.readString(1);
			
			// Process with different functions according to client's input.
			
			switch(key) {
			case "1":
				System.out.print("Please input user ID: ");
				String userId = Utility.readString(50);
				System.out.print("Please input password: ");
				String pwd = Utility.readString(50);
				// Need to verify if the user is valid from server.
				// Create a class called UserClientService (user register or login).
				if(userClientService.checkUser(userId, pwd)) {
					System.out.println("========== Welcome (user " + userId + " login succeeds ) ==========");
					// Come to second class menu.
					while (loop) {
						System.out.println("\n============ Second Menu of the network communication system (user " + userId + ")============");
						System.out.println("\t\t 1 Display list of online users");
						System.out.println("\t\t 2 Group message");
						System.out.println("\t\t 3 Private message");
						System.out.println("\t\t 4 Send file");
						System.out.println("\t\t 9 logout");
						System.out.print("Please inout your selection: ");
						key = Utility.readString(50);
						switch(key) {
						case "1": 
							// Use a method to get the online users list.
							// System.out.println("Display list of online users");
							userClientService.onlineFriendList();
							
							break;
						case "2":						
							System.out.println("Please input the message to online users group");
							String s = Utility.readString(100);
							// Recall a method to encapsulate the message and send it to server.
							messageClientService.sendMessageToAll(s, userId);
							break;
						case "3":
							System.out.print("Please  input the user id (online) you want to send a private message to: ");
							String getterId = Utility.readString(50);
							System.out.print("Please input the private message: ");
							String content = Utility.readString(100);
							// Write a method to send the message to server.
							messageClientService.sendMessageToOne(content, userId, getterId);
							
							System.out.println("Private message");
							break;
						case "4":
							System.out.print("Please input the online user that you want to send a file: ");
							getterId = Utility.readString(50);
							System.out.print("Please input the path of source: ");
							String src = Utility.readString(100);
							System.out.print("Please input the path of destination: ");
							String dest = Utility.readString(100);
							fileClientService.sendFileToOne(src, dest, userId, getterId);
							break;
						case "9":
							// Recall a method to send a message of exiting system to server.
							userClientService.logout();
							loop = false;
							break;
													
						}
					
					}
				} else {
					// Login server fails.
					
					System.out.println("=============== Login fails ============");
				}
				
				break;
			case "9":
				loop = false;
				break;
			
			}
			
		}
	}

}
