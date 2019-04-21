
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class CommunicationsMonitor {
	
	private List<ComputerTriple> tripleList;
	private HashMap<Integer, List<ComputerNode>> graph;
	
	
	//Constructor with no parameters
	public CommunicationsMonitor() {
		tripleList = new ArrayList<ComputerTriple>();
		graph = null;
	}
	
	
	
//	takes as input two integers c1,c2, and a timestamp. 
//	This triple represents the fact that the computers withIDs c1 andc2 
//	have communicated at the given timestamp. 
//	This method should run in O(1)time. 
//	Any invocation of this method after createGraph() is called will be ignored.
	public void addCommunication(int c1, int c2, int timestamp) {
		if(graph != null)
			return;
		ComputerNode temp1 = new ComputerNode(c1, timestamp);
		ComputerNode temp2 = new ComputerNode(c2, timestamp);
		ComputerTriple triple = new ComputerTriple(temp1, temp2, timestamp);
		tripleList.add(triple);
	}
	
	
//	Constructs the data structure as specified in the Section 2. 
//	This method should run in O(n+mlogm) time.
	public void createGraph() {
		tripleList.sort(new TripleComparator());
		graph = new HashMap<Integer, List<ComputerNode>>();
		for(int i = 0; i < tripleList.size(); i++) {
			ComputerTriple tempTriple = tripleList.get(i);
			tempTriple.getC1().getOutNeighbors().add(tempTriple.getC2());
			tempTriple.getC2().getOutNeighbors().add(tempTriple.getC1());
			if(graph.get(tempTriple.getTimestamp()) != null) {
				List<ComputerNode> tempList = graph.get(tempTriple.getTimestamp());
				for(int j = 0; j < tempList.size(); j++) {
					ComputerNode tempC = tempList.get(j);
					tempTriple.getC1().getOutNeighbors().add(tempC);
					tempTriple.getC2().getOutNeighbors().add(tempC);
					tempC.getOutNeighbors().add(tempTriple.getC1());
					tempC.getOutNeighbors().add(tempTriple.getC2());
				}
				tempList.add(tempTriple.getC1());
				tempList.add(tempTriple.getC2());
			}
			else {
				List<ComputerNode> tempList = new ArrayList<ComputerNode>();
				tempList.add(tempTriple.getC1());
				tempList.add(tempTriple.getC2());
				graph.put(tempTriple.getTimestamp(), tempList);
			}
			for(int j = 0; j < i; j++) {
				ComputerTriple triple = tripleList.get(j);
				if(triple.getC1().equals(tempTriple.getC1()))
					tempTriple.getC1().getOutNeighbors().add(triple.getC1());
				if(triple.getC2().equals(tempTriple.getC2()))
					tempTriple.getC2().getOutNeighbors().add(triple.getC2());
			}
		}
		
	}
	
	
	
//	Determines whether computer c2 could be infected by time y if computer c1 was infected at time x. 
//	If so, the method returns an ordered list of ComputerNode objects that represents the transmission sequence. 
//	This sequence is a path in graph G. The first ComputerNodeobject on the path will correspond to c1. 
//	Similarly, the last ComputerNodeobject on the path will correspond to c2. If c2 cannot be infected, return null.
//  O(m)	
	public List<ComputerNode> queryInfection(int c1, int c2, int x, int y){
//		ComputerNode infectedNode = null;
//		for(int i = x; i < y; i++) {
//			List<ComputerNode> temp = graph.get(i);
//			for(ComputerNode t : temp) {
//				if(t.getID() == c1) {
//					x = i;
//					infectedNode = t;
//					break;
//				}
//			}
//			break;
//		}
		List<ComputerNode> xArr = graph.get(x);
		if(xArr == null)
			return null;
		ComputerNode node = null;
		for(int i = 0; i < xArr.size(); i++) {
			node = xArr.get(i);
			if(node.getID() == c1 && node.getTimestamp() > x)
				return null;
			else if(node.getID() == c2 && node.getTimestamp() <= x)
				break;
		}
		
		if(graph.get(y) != null) {
			ComputerNode tempC2 = null;
			for(int i = 0; i < graph.get(y).size(); i++) {
				if(graph.get(y).get(i).getID() == c2)
					tempC2 = graph.get(y).get(i);
			}
			if(tempC2 == null || tempC2.getTimestamp() > y)
				return null;
			
			return DFS(node, tempC2, x, y);
		}
		return null;
	}
	
	
	
	
	
//	Returns a HashMap that represents the mapping between an Integer and a list of ComputerNodeobjects.
//	The Integer represents the ID of some computer Ci, while the list consists of pairs (Ci,t1),(Ci,t2),...,(Ci,tk), 
//	represented by ComputerNode objects, that specify that Ci has communicated with other computers at times t1,t2,...,tk. 
//	The list for each computer must be ordered by time; i.e.,t1<t2<���<tk
	public HashMap<Integer, List<ComputerNode>> getComputerMapping(){
		return graph;
	}
	
	
	
	
//	Returns the list of ComputerNodeobjects associated with computer c by performing a lookup in the mapping.
	public List<ComputerNode> getComputerMapping(int c){
		if(graph != null)
			return graph.get(c); //this is wrong because it will get time c, not computer c
		return null;
	}
	
	/**
	 * performs a DFS search on a graph, using c1 as a starting point and searching for c2.
	 * returns a list of nodes, signifying the path used to reach c2. If c2 is not found,
	 * this method returns null;
	 * @param graph
	 * @param c1
	 * @param c2
	 * @param x
	 * @param y
	 * @return
	 */
	private List<ComputerNode> DFS(ComputerNode c1, ComputerNode c2, int x, int y) {
		
		List<ComputerNode> path = new ArrayList<ComputerNode>();
		path.add(c1);
		path = DFSVisit(c1, c2, x, y, path);
		if(!path.get(path.size()- 1 ).equals(c2))
			return null;
		return path;
	}
	
	private List<ComputerNode> DFSVisit(ComputerNode c1, ComputerNode c2, int x, int y, List<ComputerNode> path){
		c1.setColor(1);
		for(int i = 0; i < c1.getOutNeighbors().size(); i++) {
			ComputerNode temp = c1.getOutNeighbors().get(i);
		}
		return path;
	}
	
	private
	
	class TripleComparator implements Comparator<ComputerTriple>{

		@Override
		public int compare(ComputerTriple c1, ComputerTriple c2) {
			if(c1.getTimestamp() < c2.getTimestamp())
				return -1;
			else if (c1.getTimestamp() > c2.getTimestamp())
				return 1;
			else
				return 0;
		}
		
	}
}
