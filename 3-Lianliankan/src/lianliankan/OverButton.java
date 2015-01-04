package lianliankan;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class OverButton extends JButton implements ActionListener{
	LocalDataCenter ldc;
	
	public OverButton(LocalDataCenter ldc) {
		this.ldc = ldc;
		this.setText("GiveUp");
		this.setFont(new Font("微软雅黑",Font.PLAIN,16));
		this.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		ldc.giveUp = true;
		ldc.push("giveUp");
		setText("Wait to agree");
	}

}
