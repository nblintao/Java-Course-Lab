package lianliankan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
	ServerSocket server;
	Socket client;
	DataCenter dc;
	public void start() throws IOException{
		server = new ServerSocket(9999);
		dc = new DataCenter();
		while(true){
			client=server.accept();
			//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			System.out.println("Server start");
			
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			oos.writeObject(dc);
			
			while(true){
				String line;
				line=br.readLine();
				if(line != null)
					System.out.println(line);
			}
			//bw.close();
		}
		
	}
}
