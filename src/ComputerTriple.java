/**
 * 
 * @author Logan Williams
 *
 */

public class ComputerTriple {

	private ComputerNode c1;
	private ComputerNode c2;
	private int timestamp;
	
	public ComputerTriple(ComputerNode temp1, ComputerNode temp2, int time) {
		c1 = temp1;
		c2 = temp2;
		timestamp = time;
	}

	/**
	 * @return the c1
	 */
	public ComputerNode getC1() {
		return c1;
	}

	/**
	 * @return the c2
	 */
	public ComputerNode getC2() {
		return c2;
	}

	/**
	 * @return the timestamp
	 */
	public int getTimestamp() {
		return timestamp;
	}
	/**
	 * checks if a triple is equal to another triple
	 * @param c
	 * @return
	 */
	public boolean equals(ComputerTriple c) {
		if(this.c1.getID() == c.getC1().getID() && this.c2.getID() == c.getC2().getID() &&
		this.getTimestamp() == c.getTimestamp())
			return true;
		
		return false;
	}
	

	
}
