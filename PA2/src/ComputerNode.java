import java.util.List;

public class ComputerNode {
	
	private int ID;
	private int timestamp;
	private List<ComputerNode> neighbors;
	private int color; // 0 for white, 1 for grey, 2 for black
	
	public ComputerNode(int id, int time) {
		ID = id;
		timestamp = time;
		setColor(0);
	}
	
	
	
	
//	Returns the ID of the associated computer.
	public int getID() {
		return ID;
	}
	
//	Returns the timestamp associated with this node.
	public int getTimestamp() {
		return timestamp;
	}
	
//	Returns a list of ComputerNode objects to which there is outgoing edge from this ComputerNode object.
	public List<ComputerNode> getOutNeighbors(){
		return neighbors;
	}
	
	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}
	
	/**
	 * @param color the color to set
	 */
	public void setColor(int color) {
		this.color = color;
	}




	public boolean equals(ComputerNode c) {
		if(this.ID == c.getID())
			return true;
		return false;
	}
}
