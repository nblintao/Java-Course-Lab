package lianliankan;

import java.io.BufferedWriter;
import java.io.IOException;

public class LocalDataCenter {
	int style;
	int height,width;
	boolean[][] map;
	int[][] typeReco;
	
	Block selectedBlock;
	
	BufferedWriter bw;
	public LocalDataCenter(DataCenter dc, BufferedWriter bw) {
		style = dc.style;
		height = dc.height;
		width = dc.width;
		map = dc.map;
		typeReco = dc.typeReco;
		
		this.bw = bw;
	}
	public int getType(int i,int j){
		return typeReco[i][j];
	}
	public void setMapFalse(int i, int j) throws IOException {
		bw.write("delete "+i+' '+j+'\n');
		bw.flush();
		map[i][j]=false;
	}
	
}
