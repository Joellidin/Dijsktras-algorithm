import java.util.*;

public class Graph{
	/**HashMap with key=label, value=vertex.*/
	private HashMap<String, Vertex> vertexLabelMap;
	
	/**
	 * Class constructor creating an empty graph.
	 */
	public Graph() {
		vertexLabelMap = new HashMap<String, Vertex>();
	}
	
	public HashMap<String, Vertex> getMap(){
		return vertexLabelMap;
	}

    public void addVertex(String label) {
    	vertexLabelMap.put(label, new Vertex(label));
    }
    
    public void addEdge(String node1, String node2, int dist) { 
    	Vertex vertex1 = vertexLabelMap.get(node1);
    	Vertex vertex2 = vertexLabelMap.get(node2);
    	vertex1.addEdge(vertex2,dist);
    	vertex2.addEdge(vertex1,dist); 
    }
    
    /**
     * Inner class describing a path.
     */
    public static class Path {
        public int totalDist;
        public List<String> vertices;
        public Path(int totalDist, List<String> vertices) {
            this.totalDist = totalDist;
            this.vertices = vertices;
        }

        @Override
        public String toString() {
            return "totalDist: " + totalDist + ", vertices: " + vertices.toString();
        }
    }
    
    /**
     * Calculates the shortest from A to B using Dijkstras algorithm.
     */
    public Path shortestPath(String start, String dest) { 
    	Vertex startVertex = vertexLabelMap.get(start);
    	Vertex destVertex = vertexLabelMap.get(dest);
    	Vertex currentVertex = startVertex;
    	PriorityQueue<Vertex> possibleTargets = new PriorityQueue<Vertex>(new VertexComparator());
    	HashSet<Vertex> visitedVertices = new HashSet<>();

    	// Add first element to visitedVertices.
    	visitedVertices.add(startVertex);
    	startVertex.setDistance(0);
    	
    	// While the destination isn't yet visited...
    	while(!visitedVertices.contains(destVertex)) { //O(nlog(n)))
    		// For each neighbor to the current vertex...
        	for(Edge edge : currentVertex.getEdges()) { //O(log(n))
        		// If the neighbor is unvisited:
        		// Add edge from currentVertex to this neighbor to possibleEdges and 
        		// calculate their distances.
        		if(!visitedVertices.contains(edge.getDest())) {
        			Vertex currentNeighbor = edge.getDest();
        			int distance = currentVertex.getDistance()+edge.getDist();
        			if(distance < currentNeighbor.getDistance()) {
        				int oldDistance = 0;
        				if(possibleTargets.contains(currentNeighbor))
        					oldDistance = currentNeighbor.getDistance(); 
        				currentNeighbor.setDistance(distance);
        				currentNeighbor.setPredecessor(currentVertex);
        				
        				// If the new neighbor is already in possibleTargets, sift it
        				if(possibleTargets.contains(currentNeighbor)) {
        					Vertex dummy = new Vertex("dummy");
        					dummy.setDistance(oldDistance);
            				possibleTargets.update(currentNeighbor, dummy);
            				possibleTargets.update(dummy, currentNeighbor);
        				}
        				
        				// If not, add it to possible targets
        				else {
        					possibleTargets.add(currentNeighbor); // O(log(n)) due to sifting in the priority queue
        				}
        			}
        		}
        	}
           	
        	// Return null if there's no possible edge.
           	if(possibleTargets.size() == 0) {
        		resetDistAndPredecessors(visitedVertices, possibleTargets);
           		return null;
        	}
			currentVertex = possibleTargets.poll();
        	visitedVertices.add(currentVertex);
    	}
    	int finalDistance = currentVertex.getDistance();
    	
    	// Go the path backwards from dest through predecessors and record the path.
    	List<String> pathVertices = new ArrayList<>();
    	pathVertices.add(dest); 
    	while(currentVertex.getPredecessor()!=null) {
    		currentVertex = currentVertex.getPredecessor();
    		pathVertices.add(currentVertex.getLabel());
    	}
    	
    	// Reverse the path the path so it is from start to dest and reset distance and predecessors.
    	Collections.reverse(pathVertices);
    	resetDistAndPredecessors(visitedVertices, possibleTargets);
    	return new Path(finalDistance, pathVertices);
    }

    /**
     * Reset distance and predecessor that were changed
     */
    public void resetDistAndPredecessors(HashSet<Vertex> visitedVertices, PriorityQueue<Vertex> possibleTargets) {
    	for(Vertex visitedVertex : visitedVertices) {
    		visitedVertex.setDistance(Integer.MAX_VALUE);
    		visitedVertex.setPredecessor(null);
    	}
    	while(possibleTargets.size() > 0) {
    		possibleTargets.peek().setDistance(Integer.MAX_VALUE);
    		possibleTargets.poll().setPredecessor(null);	
    	}
    }
}