package mi.poker.gui.tree.struct;

public class MetaInfo {
	private int x;
	private int y;
	
	private int playerNr;
	private String actionName;
	private double value;
	
	private Region region;
	private double degree;
	private int circlelvl;
	
	private TreeSize treeSize;
	
	public int getPlayerNr() {
		return this.playerNr;
	}
	public String getActionName() {
		return actionName;
	}
	public double getValue() {
		return value;
	}
	
	public void setPlayerNr(int playerNr) {
		this.playerNr = playerNr;
		
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public void setValue(double value) {
		this.value = value;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public double getDegree() {
		return degree;
	}
	public int getCirclelvl() {
		return circlelvl;
	}
	public void setDegree(double degree) {
		this.degree = degree;
	}
	public void setCirclelvl(int circlelvl) {
		this.circlelvl = circlelvl;
	}
	public TreeSize getTreeSize() {
		return treeSize;
	}
	public void setTreeSize(TreeSize treeSize) {
		this.treeSize = treeSize;
	}
}
