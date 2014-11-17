package browser;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class Address implements ActionListener{
	JTextField urlField;
	private History history;
		
	Address(History history){
		this.history = history;
		this.urlField = new JTextField();
		urlField.setFont(new Font("微软雅黑", Font.BOLD, 16));
		urlField.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		String url = e.getActionCommand();
		history.newPage(url);
	}
	public void setAddress(String url){
		urlField.setText(url);
	}
}
