package qqserver.service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import qqcommon.Message;
import qqcommon.MessageType;
import utils.Utility;

public class SendNewsToAllService implements Runnable{

	@Override




	public void run() {
		// For broadcasting news continually, use while loop.
		while(true) {

			System.out.println("Please input exit to exit broadcasting service or input the news that is going to be sent to all by server: ");
			String news = Utility.readString(100);
			if("exit".equals(news)) {
				break;
			}

			// Construct a news message.
			Message message = new Message();
			message.setSender("Server");
			message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
			message.setContent(news);
			message.setSendTime(new Date().toString());
			System.out.println("The Server broadcast to all a news: " + news);

			// Iterate current online communication threads and get sockets to reply news.
			HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
			Iterator<String> iterator = hm.keySet().iterator();
			while(iterator.hasNext()) {

				String onLineUserId = iterator.next().toString();

				try {
					ServerConnectClientThread serverConnectClientThread = hm.get(onLineUserId);
					ObjectOutputStream oos =
							new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
					oos.writeObject(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		}

	}
}
