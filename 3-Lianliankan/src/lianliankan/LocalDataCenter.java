package lianliankan;

import java.io.BufferedWriter;
import java.io.IOException;

public class LocalDataCenter {
	int style;
	int height,width;
	boolean[][] map;
	int[][] typeReco;
	
	Block selectedBlock;
	Block[][] blockCollection;
	
	BufferedWriter bw;
	public LocalDataCenter(String[] cmd, BufferedWriter bw) {
		initializeData(cmd);
		this.bw = bw;
		initializeBlockCollection();
	}
	private void initializeData(String[] info) {
		assert(info[0].equals("GameInitialize"));
		style = Integer.parseInt(info[1]);
		height = Integer.parseInt(info[2]);
		width = Integer.parseInt(info[3]);
		map = new boolean[height][width];
		typeReco = new int[height][width];
		int p=4;
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
	public void pushMapFalse(Block x, Block y) {
		try {
			bw.write("delete "+x.i+' '+x.j+' '+y.i+' '+y.j+' '+'\n');
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
}
