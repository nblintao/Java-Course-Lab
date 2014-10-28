package browser;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MyBrowser extends JFrame{
	private static final long serialVersionUID = -579949100353474747L;
	MyBrowser(){
		super("MyBrowser");
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		Toolkit toolkit = getToolkit();
		Dimension dimension = toolkit.getScreenSize();
		setBounds(dimension.width/4,dimension.height/4,dimension.width/2,dimension.height/2); 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		InitializeLayout();
		
	}
	public void InitializeLayout(){
		Box navigator = Box.createHorizontalBox();
		
		JButton buttonBackward = new JButton("Backward");
		navigator.add(buttonBackward);

		JButton buttonForward = new JButton("Forward");
		navigator.add(buttonForward);
		
		JButton buttonRefresh = new JButton("Refresh");
		navigator.add(buttonRefresh);

//		BrowseListener browseListener = new BrowseListener();
		JTextField urlField = new JTextField();
//		urlField.addActionListener(browseListener);
		urlField.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("brose: " + e.getActionCommand());						
					}
				}
		);
		
		navigator.add(urlField);

		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		
		content.add(navigator, BorderLayout.NORTH);

		TextArea txt = new TextArea();

		content.add(txt, BorderLayout.CENTER);

//		JTextField stu = new JTextField();
//		content.add(stu, BorderLayout.SOUTH);
	}
	public static void main(String[] argv){
		MyBrowser myBrowser = new MyBrowser();
		myBrowser.setVisible(true);
	}
}
