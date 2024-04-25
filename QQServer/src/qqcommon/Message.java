package qqcommon;

import java.io.Serializable;

/**
 * 
 * @author CSCI690_Team_5
 *
 * Object of message communicate between client and server.
 *
 */

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String sender;
	private String getter;
	private String content;
	private String sendTime;
	private String mesType;// Message type can be defined in the interface.
	
	// Extend file related members.
		private byte[] fileBytes;
		private int fileLen = 0;
		private String dest; // The file sending destination path.
		private String src; // The file sending source path.
		
		
	
	
	
	public byte[] getFileBytes() {
			return fileBytes;
		}
		public void setFileBytes(byte[] fileBytes) {
			this.fileBytes = fileBytes;
		}
		public int getFileLen() {
			return fileLen;
		}
		public void setFileLen(int fileLen) {
			this.fileLen = fileLen;
		}
		public String getDest() {
			return dest;
		}
		public void setDest(String dest) {
			this.dest = dest;
		}
		public String getSrc() {
			return src;
		}
		public void setSrc(String src) {
			this.src = src;
		}
	public String getMesType() {
		return mesType;
	}
	public void setMesType(String mesType) {
		this.mesType = mesType;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getGetter() {
		return getter;
	}
	public void setGetter(String getter) {
		this.getter = getter;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
	

}
