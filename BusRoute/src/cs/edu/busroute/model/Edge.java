package cs.edu.busroute.model;

/**
 * Describe the edge between two stations
 * 
 * @author HoangNguyen
 * 
 */
public class Edge {
	private final String source;
	private final String destination;
	private final double weight;

	public Edge(String source, String destination, double weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public double getWeight() {
		return weight;
	}

}
