package lianliankan;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Block extends JButton {
	private static final long serialVersionUID = 1963880243205418028L;
	int type;
	int typeAmount;
	Block(int typeAmount){
		this.typeAmount = typeAmount;
		type = (int) Math.floor(Math.random()*typeAmount);
		setIcon(new ImageIcon("./Icon/" + type + ".png"));
		
	}
}
