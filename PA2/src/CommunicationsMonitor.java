
import java.util.HashMap;
import java.util.List;

public class CommunicationsMonitor {
	
	private HashMap<Integer, List<ComputerNode>> graph;
	
	
	//Constructor with no parameters
	public CommunicationsMonitor() {
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
		
	}
	
	
//	Constructs the data structure as specified in the Section 2. 
//	This method should run in O(n+mlogm) time.
	public void createGraph() {
		
	}
	
	
	
//	Determines whether computer c2 could be infected by time y if computer c1 was infected at time x. 
//	If so, the method returns an ordered list of ComputerNode objects that represents the transmission sequence. 
//	This sequence is a path in graph G. The first ComputerNodeobject on the path will correspond to c1. 
//	Similarly, the last ComputerNodeobject on the path will correspond to c2. If c2 cannot be infected, return null.
//  O(m)	
	public List<ComputerNode> queryInfection(int c1, int c2, int x, int y){
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
			return graph.get(c);
		return null;
	}
}
