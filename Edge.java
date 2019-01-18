/*
 * A class describing an edge.
 */
public class Edge{
	private Vertex dest;
	private int dist;
	public Edge(Vertex dest, int dist) {
		this.dest = dest;
		this.dist = dist;
	}
	/** Returns destination vertex.*/
	public Vertex getDest() {
		return dest;
	}
	public int getDist() {
		return dist;
	}
}