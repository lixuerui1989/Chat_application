package qqcommon;

/**
 * 
 * @author CSCI690_Team_5
 * 
 * For representing message types.
 *
 */

public interface MessageType {
	
	String MESSAGE_LOGIN_SUCCEED = "1"; // Login succeeds.
	String MESSAGE_LOGIN_FAIL = "2"; // Login fails.
	String MESSAGE_COMM_MES = "3"; // Normal one to one message packet.
	String MESSAGE_GET_ONLINE_FRIED = "4"; // Request to return a list of online users.
	String MESSAGE_RET_ONLINE_FRIED = "5"; // returned a list of online users.
	String MESSAGE_CLIENT_EXIT = "6"; // client requests to exit.
	String MESSAGE_TO_ALL_MES = "7"; // Group chat message packet.
	String MESSAGE_FILE_MES = "8"; // File message(sending file).
	
}
