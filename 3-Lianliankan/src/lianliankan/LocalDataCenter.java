package lianliankan;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;

public class LocalDataCenter {
	int style;
	int height,width;
	boolean[][] map;
	int[][] typeReco;
	
	Block selectedBlock;
	Block[][] blockCollection;
	
	BufferedWriter bw;
	
	JLabel myScore;
	JLabel otherScore;
	OverButton overButton;
	
	Map<Integer, Integer> scoreList;
	
	int id;
	
	boolean giveUp;
	
	public LocalDataCenter(String[] cmd, BufferedWriter bw) {
		initializeData(cmd);
		this.bw = bw;
		this.giveUp = false;
		initializeBlockCollection();
	}
	private void initializeData(String[] info) {
		assert(info[0].equals("GameInitialize"));
		style = Integer.parseInt(info[1]);
		height = Integer.parseInt(info[2]);
		width = Integer.parseInt(info[3]);
		id = Integer.parseInt(info[4]);
		map = new boolean[height][width];
		typeReco = new int[height][width];
		int p=5;
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				map[i][j] = Boolean.parseBoolean(info[p++]);
			}
		}
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				typeReco[i][j] = Integer.parseInt(info[p++]);
			}
		}				
	}
	public int getType(int i,int j){
		return typeReco[i][j];
	}
	public void push(String s){
		try {
			bw.write(s+'\n');
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void pushMapFalse(Block x, Block y) {
		push("delete "+x.i+' '+x.j+' '+y.i+' '+y.j+' ');
	}
	public void pullMapFalse(int xi, int xj, int yi, int yj) {
		setBlockFalse(xi, xj);
		setBlockFalse(yi, yj);
	}
	private void setBlockFalse(int i, int j) {
		map[i][j] = false;
		Block block = blockCollection[i][j];
		block.setEnabled(false);
		if(selectedBlock == block){
			selectedBlock = null;
		}
	}
	public void initializeBlockCollection() {
		blockCollection = new Block[height][width];
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				blockCollection[i][j] = new Block(i, j, this);
			}
		}
	}
	public void updateScoreDisplay() {
		myScore.setText(Integer.toString(scoreList.get(id)));
		
		String other = "";
		Iterator<Entry<Integer, Integer>> it = scoreList.entrySet().iterator();
		while(it.hasNext()){
			Entry<Integer, Integer> entry = it.next();
			if(entry.getKey() != id)
				other += "player[" + entry.getKey() +"] " +entry.getValue() +"    ";
		}
		otherScore.setText(other);
	}	
}
