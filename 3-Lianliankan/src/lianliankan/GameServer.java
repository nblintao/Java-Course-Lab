package lianliankan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
	ServerSocket server;
	Socket client;
	public void start() throws IOException{
		server = new ServerSocket(9999);
		while(true){
			client=server.accept();
			
		}
		
	}
	public static void main(){
		GameServer gs = new GameServer();
		try {
			gs.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
