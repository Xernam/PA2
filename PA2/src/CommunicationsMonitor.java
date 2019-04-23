
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import CommunicationsMonitor.TripleComparator;

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
		
		tripleList.sort(new TripleComparator()); //sort the triples (step 1);
		timestampGraph = new HashMap<Integer, List<ComputerNode>>(); //Creation of 2 maps, step 2.
		nodeGraph = new HashMap<Integer, List<ComputerNode>>();
		
		for(int i = 0; i < tripleList.size(); i++) { // Scan triple list, step 3
			ComputerTriple triple = tripleList.get(i);
			ComputerNode C1 = triple.getC1();
			ComputerNode C2 = triple.getC2();
			List<ComputerNode> C1Neighbors = C1.getOutNeighbors();
			List<ComputerNode> C2Neighbors = C2.getOutNeighbors();
			
			C1Neighbors.add(C2); // add directed edge from c1 to c2
			C2Neighbors.add(C1); // add directed edge from c2 to c1
			if(timestampGraph.get(triple.getTimestamp()) != null) {
				List<ComputerNode> timestampList = timestampGraph.get(triple.getTimestamp()); //create new nodes in list if they dont exist
				timestampList.add(C1);
				timestampList.add(C2);
			}
			if(nodeGraph.get(C1.getID()) == null) { // create new nodes in nodeGraph if they dont already exist
				nodeGraph.put(C1.getID(), new ArrayList<ComputerNode>());
			}
			if(nodeGraph.get(C2.getID()) == null) {
				nodeGraph.put(C2.getID(), new ArrayList<ComputerNode>());
			}
			nodeGraph.get(C1.getID()).add(C1); //append reference to list of c1 nodes
			nodeGraph.get(C1.getID()).add(C2); //append reference to list of c2 nodes
			
			for(ComputerNode node : nodeGraph.get(C1.getID())) {
				if(C1.getID() == node.getID() && C1.getTimestamp() != node.getTimestamp()) // directed edge to the last node in the list
					node.getOutNeighbors().add(C1);
			}
			for(ComputerNode node : nodeGraph.get(C2.getID())) {
				if(C2.getID() == node.getID() && C2.getTimestamp() != node.getTimestamp()) // directed edge to the last node in the list
					node.getOutNeighbors().add(C2);
			}
				
		}
		
		//TODO Test this extensively, I think this is correct, but I may be forgetting some cases..
		

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
		
		boolean c1found = false;
		boolean c2found = false;
		for(int i = 0; i < this.nodeGraph.size(); i++)
		{
			if(this.nodeGraph.getId() == c1 && this.nodeGraph.getId() == c2) {
				if(this.nodeGraph.getTime() > x && this.nodeGraph.getTime() < y) {
					ComputerNode c1Node = new ComputerNode(c1, x);
					ComputerNode c2Node = new ComputerNode(c2, y);
					return DFS(c1Node, c2Node, x, y);
				}
			}
		}
		
		return null;
	}
	
	
	
	
	
//	Returns a HashMap that represents the mapping between an Integer and a list of ComputerNodeobjects.
//	The Integer represents the ID of some computer Ci, while the list consists of pairs (Ci,t1),(Ci,t2),...,(Ci,tk), 
//	represented by ComputerNode objects, that specify that Ci has communicated with other computers at times t1,t2,...,tk. 
//	The list for each computer must be ordered by time; i.e.,t1<t2<���<tk
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
