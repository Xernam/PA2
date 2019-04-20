import java.util.Comparator;

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
	
	public int compare(ComputerTriple c) {
		if(this.getTimestamp() < c.getTimestamp())
			return -1;
		else if(this.getTimestamp() > c.getTimestamp())
			return 1;
		else
			return 0;
	}
	
	public boolean equals(ComputerTriple c) {
		if(this.c1.getID() == c.getC1().getID() && this.c2.getID() == c.getC2().getID() &&
		this.getTimestamp() == c.getTimestamp())
			return true;
		
		return false;
	}
	

	
}
