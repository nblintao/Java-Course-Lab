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
	public void setMapFalse(Block x, Block y) {
		try {
			bw.write("delete "+x.i+' '+x.j+' '+y.i+' '+y.j+' '+'\n');
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		map[x.i][x.j]=false;
//		map[y.i][y.j]=false;
	}
	
}
