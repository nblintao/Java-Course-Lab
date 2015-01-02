package lianliankan;

public class ClientMainThread extends Thread {
	ClientMainThread(){
		start();
	}
	public void run(){
		new Client();
		new Client();
	}
}