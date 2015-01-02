package lianliankan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMainThread extends Thread {
	ServerSocket server;
//	Socket client;
	DataCenter dc;

	ServerMainThread(){
		start();
	}
	public void run(){
		try {
			server = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server start");
		dc = new DataCenter();
		while(true){
			Socket client;
			try {
				client = server.accept();
				dc.clientList.add(client);
				System.out.println("Server accept new client");
				ServerThread st = new ServerThread(dc, client);
				st.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
