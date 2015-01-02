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
	public LocalDataCenter(String line, BufferedWriter bw) {
		String[] info = line.split(" ");
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
