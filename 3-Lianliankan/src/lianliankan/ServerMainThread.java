package lianliankan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMainThread extends Thread {
	ServerSocket server;
//	Socket client;
	DataCenter dc;
	Stop stop;
	
	ServerMainThread(Stop stop){
		this.stop = stop;
		start();
	}
	public void run(){
		try {
			server = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server start");
		dc = new DataCenter(this);
		while(true){
			Socket client;
			try {
				client = server.accept();
				System.out.println("Server accept new client");
				ServerThread st = new ServerThread(dc, client);
				dc.serverThreadList.add(st);
				st.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
