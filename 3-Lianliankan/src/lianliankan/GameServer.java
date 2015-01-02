package lianliankan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
	ServerSocket server;
//	Socket client;
	DataCenter dc;
	public void start() throws IOException{
		server = new ServerSocket(9999);
		System.out.println("Server start");
		dc = new DataCenter();
		while(true){
			Socket client=server.accept();
			dc.clientList.add(client);
			System.out.println("Server accept new client");
			ServerThread st = new ServerThread(dc, client);
			st.start();
		}
		
	}
}
