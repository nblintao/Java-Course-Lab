package lianliankan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Block extends JButton implements ActionListener {
	private static final long serialVersionUID = 1963880243205418028L;
	DataCenter dc;
	int type;
	int i;
	int j;
	Block(int i, int j, DataCenter dc){
		this.dc = dc;
		this.i = i;
		this.j = j;
		dc.map[i][j] = true;
		type = (int) Math.floor(Math.random()*dc.typeAmount);
		setIcon(new ImageIcon("./Icon/" + type + ".png"));
		this.addActionListener(this);
	}
//	setEnabled(boolean b)  
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(dc.selectedBlock == this){
			unselect();
		}else if(dc.selectedBlock == null){
			select();
		}else{
			if(linkable(this, dc.selectedBlock)){
				this.eliminate();
				dc.selectedBlock.eliminate();
			}
			dc.selectedBlock.unselect();
		}
	}
	public void eliminate() {
		dc.map[i][j] = false;
		this.setEnabled(false);
	}
	private boolean linkable(Block b1, Block b2) {
		if(b1.type != b2.type)
			return false;
		
		return true;
	}
	private void select() {
		dc.selectedBlock = this;
		this.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 6));
	}
	private void unselect() {
		dc.selectedBlock = null;
		this.setBorder(null);
	}
}
