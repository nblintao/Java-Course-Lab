package lianliankan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;

public class ServerThread extends Thread {
	DataCenter dc;
	Socket client;
	BufferedWriter bw;
	int id;
	int score;
	static int nextId = 0;
	
	public ServerThread(DataCenter dc, Socket client) {
		this.dc = dc;
		this.client = client;
		id = nextId++;
		score = 0;
	}

	public void run(){
		BufferedReader br;
		
//		ObjectOutputStream oos;
		String line;
		
		System.out.println("Sub-server start");
		try {
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
//			oos = new ObjectOutputStream(client.getOutputStream());
//			oos.writeObject(dc);
//			oos.close();
			
			bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));			

			bw.write(dc.getInfo()+'\n');
			bw.flush();
			
			while(true){
				line=br.readLine();
				if(line != null){
					executeInput(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void executeInput(String line) {
		System.out.println("Server receive: "+line);
		String[] cmd = line.split(" ");
		switch(cmd[0]){
		case "delete":
			deletePair(line);
			break;			
		default:
			System.out.println("Unknown command");				
		}
	}

	private void deletePair(String line) {
		sendToAll(line);
		score += dc.scoreForPair;
		sendToAll(dc.getScoreInfo());
	}

	private void sendToAll(String line) {
		System.out.println("Send to all: "+line);
		Iterator<ServerThread> it = dc.serverThreadList.iterator();
		while(it.hasNext()){
			BufferedWriter bw = it.next().bw;
			try {
				bw.write(line+'\n');
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
}
