package browser;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MyBrowser extends JFrame{
	MyBrowser(){
		super("MyBrowser");
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		Toolkit toolkit = getToolkit();
		Dimension dimension = toolkit.getScreenSize();
		setBounds(0,0,dimension.width,dimension.height);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public static void main(String[] argv){
		MyBrowser myBrowser = new MyBrowser();
		myBrowser.setVisible(true);
	}
}
