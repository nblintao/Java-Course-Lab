package lianliankan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
	ServerSocket server;
	Socket client;
	DataCenter dc;
	public void start() throws IOException{
		server = new ServerSocket(9999);
		System.out.println("Server start");
		dc = new DataCenter();
		while(true){
			client=server.accept();
			System.out.println("Server accept new client");
			ServerThread st = new ServerThread(dc, client);
			st.start();
		}
		
	}
}
