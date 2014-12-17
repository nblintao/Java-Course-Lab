package lianliankan;

import java.awt.Graphics;

public class DataCenter {
	int width = 12;
	int height = 6;	
	int typeAmount = 5;
	Block selectedBlock = null;
	boolean[][] map;
	DataCenter(){
		map = new boolean[height][width];
	}
	
}
