/**
 * @author Logan Williams
 */

import java.util.LinkedList;
import java.util.List;

public class ComputerNode {
	
	private int ID;
	private int timestamp;
	private List<ComputerNode> neighbors;
	private int color; // 0 for white, 1 for grey
	private ComputerNode pred;
	
	/**
	 * Constructs a new ComputerNode object with an id and a time
	 * @param id
	 * @param time
	 */
	public ComputerNode(int id, int time) {
		ID = id;
		timestamp = time;
		setColor(0);
		neighbors = new LinkedList<ComputerNode>();
		pred = null;
	}
	
	/**
	* Returns the ID of the associated computer.
	* @return
	*/
	public int getID() {
		return ID;
	}
	
    /**
     * Returns the timestamp associated with this node.
     * @return
     */
	public int getTimestamp() {
		return timestamp;
	}
	
//	Returns a list of ComputerNode objects to which there is outgoing edge from this ComputerNode object.
	/**
	 * Returns a list of ComputerNode objects to which there is outgoing edge
	 * @return
	 */
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
	
	/**
	 * get this node's predecessor
	 * @return
	 */
	public ComputerNode getPred() {
		return pred;
	}
	
	/**
	 * sets the predecessor to this node
	 * @param p
	 */
	public void setPred(ComputerNode p) {
		pred = p;
	}

	/**
	 * compares the id and timestamp of this against computer c
	 * @param c
	 * @return true if this == c, false otherwise.
	 */
	public boolean equals(ComputerNode c) {
		if(c == null && this != null)
			return false;
		if(c == this)
			return true;
		if(this.getID() == c.getID() && this.getTimestamp() == c.getTimestamp())
			return true;
		return false;
	}
}
