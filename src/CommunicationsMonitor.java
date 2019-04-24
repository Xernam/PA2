import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CommunicationsMonitor {
	
	private List<ComputerTriple> tripleList;
	private HashMap<Integer, List<ComputerNode>> nodeGraph; // need to create a graph of this somehow
	
	
	//Constructor with no parameters
	public CommunicationsMonitor() {
		tripleList = new LinkedList<ComputerTriple>();
		nodeGraph = null;
	}
	
	
	
//	takes as input two integers c1,c2, and a timestamp. 
//	This triple represents the fact that the computers withIDs c1 andc2 
//	have communicated at the given timestamp. 
//	This method should run in O(1)time. 
//	Any invocation of this method after createGraph() is called will be ignored.
	public void addCommunication(int c1, int c2, int timestamp) {
		if(nodeGraph != null)
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
		nodeGraph = new HashMap<Integer, List<ComputerNode>>();
		
		for(int i = 0; i < tripleList.size(); i++) { // Scan triple list, step 3
			ComputerTriple triple = tripleList.get(i);
			ComputerNode C1 = triple.getC1();
			ComputerNode C2 = triple.getC2();
			
			if(nodeGraph.get(C1.getID()) == null) { // create new nodes in nodeGraph if they dont already exist
				nodeGraph.put(C1.getID(), new LinkedList<ComputerNode>());
				nodeGraph.get(C1.getID()).add(C1);
			}
			if(nodeGraph.get(C2.getID()) == null) {
				nodeGraph.put(C2.getID(), new LinkedList<ComputerNode>());
				nodeGraph.get(C2.getID()).add(C2);
			}
			for(int j = 0; j < nodeGraph.get(C1.getID()).size(); j++) {
				ComputerNode temp = nodeGraph.get(C1.getID()).get(j);
				if(!C1.equals(temp)) {
					nodeGraph.get(C1.getID()).add(C1);
					temp.getOutNeighbors().add(C1);
				}
				else
					temp.getOutNeighbors().add(C2);
			}
			for(int j = 0; j < nodeGraph.get(C2.getID()).size(); j++) {
				ComputerNode temp = nodeGraph.get(C2.getID()).get(j);
				if(!C2.equals(temp)) {
					nodeGraph.get(C2.getID()).add(C2); //append reference to list of c2 nodes
					temp.getOutNeighbors().add(C2);
				}
				else
					temp.getOutNeighbors().add(C1);
			}
		}
	}
	
	/**
	 * Determines whether computer c2 could be infected by time y if computer c1 was infected at time x. 
	 * @param c1 - computer 1
	 * @param c2 - computer 2
	 * @param x - time that c1 was infected
	 * @param y - time to infect computer 2
	 * @return list of nodes traversed to reach computer 2
	 */
	public List<ComputerNode> queryInfection(int c1, int c2, int x, int y){
		return BFS(c1, c2, x, y);
	}	
	
	/**
	 * Returns a HashMap that represents the mapping between an Integer and a list of ComputerNodeobjects.
	 * @return nodeGraph - hashMap that represents the mapping between an Integer and a list
	 */
	public HashMap<Integer, List<ComputerNode>> getComputerMapping(){
		return nodeGraph;
	}
	
//	Returns the list of ComputerNodeobjects associated with computer c by performing a lookup in the mapping.
	public List<ComputerNode> getComputerMapping(int c){
		if(nodeGraph != null)
			return nodeGraph.get(c);
		return null;
	}
	
	private void uncolorList() {
		for(ComputerTriple triple : tripleList) {
			for(ComputerNode node : nodeGraph.get(triple.getC1().getID()))
				node.setColor(0);
			for(ComputerNode node : nodeGraph.get(triple.getC2().getID()))
				node.setColor(0);
		}
	}
	
	private List<ComputerNode> BFS(int c1, int c2, int x, int y){
		uncolorList();
		LinkedList<ComputerNode> queue = new LinkedList<ComputerNode>();
		LinkedList<ComputerNode> array = new LinkedList<ComputerNode>();
		ComputerNode node = null;
		ComputerNode endNode = null;
		if(nodeGraph.get(c1) == null || nodeGraph.get(c2) == null)
			return null;
		for(int i = 0; i < nodeGraph.get(c1).size(); i++) {
			if(nodeGraph.get(c1).get(i).getTimestamp() >= x) {
				node = nodeGraph.get(c1).get(i);
				break;
			}
		}
		if(node == null)
			return null;
		node.setColor(1);
		queue.addFirst(node);
		while(!queue.isEmpty()) {
			ComputerNode temp = queue.poll();
			if(temp.getID() == c2 && temp.getTimestamp() <= y) {
				endNode = temp;
				break;
			}
			for(ComputerNode neighbor : temp.getOutNeighbors()) {
				if(neighbor.getColor() == 0) {
					queue.add(neighbor);
					neighbor.setColor(1);
					neighbor.setPred(temp);
				}
			}
		}
		if(endNode != null) {
			ComputerNode temp = endNode.getPred();
			array.addFirst(endNode);
			while(temp != null) {
				array.addFirst(temp);
				temp = temp.getPred();
			}
			return array;
		}
		return null;
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