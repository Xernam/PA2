
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class CommunicationsMonitor {
	
	private List<ComputerTriple> tripleList;
	private HashMap<Integer, List<ComputerNode>> nodeGraph; // need to create a graph of this somehow
	private HashMap<Integer, List<ComputerNode>> timestampGraph;
	
	
	//Constructor with no parameters
	public CommunicationsMonitor() {
		tripleList = new ArrayList<ComputerTriple>();
		timestampGraph = null;
		nodeGraph = null;
	}
	
	
	
//	takes as input two integers c1,c2, and a timestamp. 
//	This triple represents the fact that the computers withIDs c1 andc2 
//	have communicated at the given timestamp. 
//	This method should run in O(1)time. 
//	Any invocation of this method after createGraph() is called will be ignored.
	public void addCommunication(int c1, int c2, int timestamp) {
		if(timestampGraph != null)
			return;
		ComputerNode temp1 = new ComputerNode(c1, timestamp);
		ComputerNode temp2 = new ComputerNode(c2, timestamp);
		ComputerTriple triple = new ComputerTriple(temp1, temp2, timestamp);
		tripleList.add(triple);
	}
	
	
//	Constructs the data structure as specified in the Section 2. 
//	This method should run in O(n+mlogm) time.
	public void createGraph() {
		
		//TODO
		//check the README, this is mostly just generating the two hash maps
		//that are needed. This does need some work on it, I think
		
		tripleList.sort(new TripleComparator());
		timestampGraph = new HashMap<Integer, List<ComputerNode>>();
		nodeGraph = new HashMap<Integer, List<ComputerNode>>();
		
		for(int i = 0; i < tripleList.size(); i++) {
			ComputerTriple tempTriple = tripleList.get(i);
			ComputerNode C1 = tempTriple.getC1();
			ComputerNode C2 = tempTriple.getC2();
			C1.getOutNeighbors().add(C2);
			C2.getOutNeighbors().add(C1);
			if(timestampGraph.get(tempTriple.getTimestamp()) != null) {
				List<ComputerNode> tempList = timestampGraph.get(tempTriple.getTimestamp());
				for(int j = 0; j < tempList.size(); j++) {
					ComputerNode tempC = tempList.get(j);
					C1.getOutNeighbors().add(tempC);
					C2.getOutNeighbors().add(tempC);
					tempC.getOutNeighbors().add(C1);
					tempC.getOutNeighbors().add(C2);
				}
				tempList.add(C1);
				tempList.add(C2);
			}
			// Beginning work on the actual graph, represented with a hashmap. Currently have first 5 of 7 done
			// in section 2. Might have to think about how to do this more, as im not entirely sure as to what
			// I am doing for this. A possible solution would be to create a graph data type with a map of
			// neighbors on the inside. Gonna look it over in the morning to see if a fresh brain will be able to work better
			//		-Logan
			
			if(nodeGraph.get(C1.getID()) == null) {
				nodeGraph.put(C1.getID(), new ArrayList<ComputerNode>());
			}
			if(nodeGraph.get(C2.getID()) == null) {
				nodeGraph.put(C2.getID(), new ArrayList<ComputerNode>());
			}
			
			nodeGraph.get(C1.getID()).add(C2);
			nodeGraph.get(C2.getID()).add(C1);
			nodeGraph.get(C1.getID()).add(C1);
			nodeGraph.get(C2.getID()).add(C2);
			
			if(timestampGraph.get(tempTriple.getTimestamp()) == null) {
				List<ComputerNode> tempList = new ArrayList<ComputerNode>();
				tempList.add(C1);
				tempList.add(C2);
				timestampGraph.put(tempTriple.getTimestamp(), tempList);
			}
			for(int j = 0; j < i; j++) {
				ComputerTriple triple = tripleList.get(j);
				if(triple.getC1().equals(C1))
					C1.getOutNeighbors().add(triple.getC1());
				if(triple.getC2().equals(C2))
					C2.getOutNeighbors().add(triple.getC2());
			}
		}
		
	}
	
	
	
//	Determines whether computer c2 could be infected by time y if computer c1 was infected at time x. 
//	If so, the method returns an ordered list of ComputerNode objects that represents the transmission sequence. 
//	This sequence is a path in graph G. The first ComputerNodeobject on the path will correspond to c1. 
//	Similarly, the last ComputerNodeobject on the path will correspond to c2. If c2 cannot be infected, return null.
//  O(m)	
	public List<ComputerNode> queryInfection(int c1, int c2, int x, int y){
		
		// What needs to be done here is to check the nodeGraph for a computer
		// C1 that has time > x in the list attached to the c1 key, and check for
		// a C2 that has time < y in the list attached to the c2 key. If both are
		// true, then perform either DFS or BFS from C1 to C2, and return the list
		// of nodes traversed. Choose either one you think will be easier (in terms
		// of building the list, I think BFS would be better).
		
		//TODO

		return null;
	}
	
	
	
	
	
//	Returns a HashMap that represents the mapping between an Integer and a list of ComputerNodeobjects.
//	The Integer represents the ID of some computer Ci, while the list consists of pairs (Ci,t1),(Ci,t2),...,(Ci,tk), 
//	represented by ComputerNode objects, that specify that Ci has communicated with other computers at times t1,t2,...,tk. 
//	The list for each computer must be ordered by time; i.e.,t1<t2<···<tk
	public HashMap<Integer, List<ComputerNode>> getComputerMapping(){
		return timestampGraph;
	}
	
	
	
	
//	Returns the list of ComputerNodeobjects associated with computer c by performing a lookup in the mapping.
	public List<ComputerNode> getComputerMapping(int c){
		if(timestampGraph != null)
			return nodeGraph.get(c);
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
