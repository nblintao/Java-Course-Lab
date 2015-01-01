package lianliankan;

import java.io.IOException;

public class ServerMainThread extends Thread {
	public void run(){
		GameServer gs = new GameServer();
		try {
			gs.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
