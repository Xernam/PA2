import java.util.List;

public class ComputerNode {
	
	private int ID;
	private int timestamp;
	private List<ComputerNode> neighbors;
	
	public ComputerNode(int id, int time) {
		ID = id;
		timestamp = time;
		neighbors = new List<ComputerNode>();
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
}
