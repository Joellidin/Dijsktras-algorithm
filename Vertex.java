import java.util.*;

/**
 * A class describing a vertex.
 */
public class Vertex{
	/**
	 * Instance variables.
	 */
	private String label;
	private List<Edge> edges;
	private int distance;
	private Vertex predecessor;
	
	/**
	 * Class constructor.
	 */
	public Vertex(String label) {
		this.label = label;
		edges = new ArrayList<>();
		distance = Integer.MAX_VALUE;
	}
	
	public void addEdge(Vertex destination, int dist) {
		edges.add(new Edge(destination, dist));
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public List<Edge> getEdges(){
		return edges;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setPredecessor(Vertex predecessor) {
		this.predecessor = predecessor;
	}
	
	public Vertex getPredecessor() {
		return predecessor;
	}
	
	/**
	 * Help method that prints out info about the vertex.  
	 */
	public void printVertexInfo() {
		System.out.println("Number of edges is "+edges.size()+".\n\n");
		for(int i=0; i<edges.size();i++) {
			String line1 = "Edge "+(i+1)+" is from "+this.label+" to "+edges.get(i).getDest().getLabel()+".";
			String line2 = "Distance is "+edges.get(i).getDist()+".\n";
			System.out.println(line1);
			System.out.println(line2);
		}
	}
}