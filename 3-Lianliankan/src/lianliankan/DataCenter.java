package lianliankan;



public class DataCenter {
	int style = 1;
	int typeAmount = 23;
	int typeAmountNeed = 15;
	int typeStart;
	int width = 12;
	int height = 6;	
	Block selectedBlock = null;
	boolean[][] map;

	DataCenter(){
		map = new boolean[height][width];
		generateType();
	}
	private void generateType() {
		typeStart = (int) (Math.random()*typeAmount);
	}
	public int getTpye() {
		return (typeStart + (int)(Math.random()*typeAmountNeed))%typeAmount;
	}
	
}
