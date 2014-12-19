package lianliankan;



public class DataCenter {
	int style = 1;
	int typeAmount = 23;
	int typeAmountNeed = 12;// can be divided by width * height / 2
	int typeStart;
	
	int[] typeAvail;
	int typeAvailN;
	
	int width = 12;
	int height = 6;	
	Block selectedBlock = null;
	boolean[][] map;

	DataCenter(){
		map = new boolean[height][width];
		generateType();
	}
	private void generateType() {
		int pairs = width*height/typeAmountNeed/2;
		assert(width*height == pairs*typeAmountNeed*2);
		typeAvail = new int[width*height];
		typeAvailN = 0;
		
		typeStart = (int) (Math.random()*typeAmount);
		for(int i=0;i<typeAmountNeed;i++){
			int t = (typeStart+i)%typeAmount;
			for(int j=0;j<pairs*2;j++){
				typeAvail[typeAvailN++]=t;
			}
		}
	}
	public int getTpye() {
		int pos = (int)(Math.random()*typeAvailN);
		int type = typeAvail[pos];
		typeAvail[pos] = typeAvail[--typeAvailN];
		return type;
	}
	
}
