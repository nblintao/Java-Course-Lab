package lianliankan;

public class ClientMainThread extends Thread {
	ClientMainThread(){
		start();
	}
	public void run(){
		new ClientThread();
		new ClientThread();
		new ClientThread();
	}
}