package lianliankan;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Block extends JButton implements ActionListener {
	private static final long serialVersionUID = 1963880243205418028L;
	LocalDataCenter dc;
	int type;
	int i;
	int j;
	Block(int i, int j, LocalDataCenter dc){
		this.dc = dc;
		this.i = i;
		this.j = j;
		dc.map[i][j] = true;
		type = dc.getType(i,j);
		setIcon(new ImageIcon("./Icon/" + dc.style + "/" + type + ".png"));
		this.addActionListener(this);
		this.setBorder(null);
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
				dc.setMapFalse(this, dc.selectedBlock);
				this.eliminate();
				dc.selectedBlock.eliminate();
			}
			dc.selectedBlock.unselect();
		}
	}
	public void eliminate() {
//		dc.map[i][j] = false;
		this.setEnabled(false);
	}
	private boolean linkable(Block b1, Block b2) {
		if(b1.type != b2.type)
			return false;
		dc.map[b1.i][b1.j]=false;
		dc.map[b2.i][b2.j]=false;		
		for(int ai=0;ai<dc.height;ai++){
			for(int aj=0;aj<dc.width;aj++){
				for(int bi=0;bi<dc.height;bi++){
					for(int bj=0;bj<dc.width;bj++){
						if(line(b1.i,b1.j,ai,aj)&&line(ai,aj,bi,bj)&&line(bi,bj,b2.i,b2.j)){
							dc.map[b1.i][b1.j]=true;
							dc.map[b2.i][b2.j]=true;
							return true;
						}
					}
				}
			}
		}
		dc.map[b1.i][b1.j]=true;
		dc.map[b2.i][b2.j]=true;
		return false;
	}
	private boolean line(int ai, int aj, int bi, int bj) {
		if(ai==bi){
			for(int t=Math.min(aj, bj);t<=Math.max(aj, bj);t++){
//				System.out.println(ai+" "+t);
				if(dc.map[ai][t]==true){
					return false;
				}
			}
			return true;
		}
		if(aj==bj){
			for(int t=Math.min(ai, bi);t<=Math.max(ai, bi);t++){
//				System.out.println(aj+" "+t);
				if(dc.map[t][aj]==true){
					return false;
				}
			}
			return true;
		}		
		return false;
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
