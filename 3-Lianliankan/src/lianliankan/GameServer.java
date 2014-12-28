package lianliankan;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
	ServerSocket server;
	Socket client;
	public void start() throws IOException{
		server = new ServerSocket(9999);
		while(true){
			client=server.accept();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			System.out.println("xie:ni hou a!");
			bw.write("ni hou a!");
			bw.close();
		}
		
	}
}
