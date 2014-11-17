package browser;

import java.util.Vector;

import javax.swing.JTextField;

public class Status {
	Vector<String> allInfo;
	public JTextField statusField;
	Status(){
		statusField = new JTextField();
		statusField.setEditable(false);
		allInfo = new Vector<String>();
	}
	public void newInfo(String info){
		allInfo.add(info);
		System.out.println(info);
		statusField.setText(info);
	}
}
