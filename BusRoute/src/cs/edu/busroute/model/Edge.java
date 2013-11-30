package cs.edu.busroute.model;

import java.util.HashSet;
import java.util.Set;

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
	private final Set<Long> ids = new HashSet<Long>();

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

	public Set<Long> getIds() {
		return ids;
	}

	public String printListBusId() {
		StringBuilder str = new StringBuilder();
		for (Long id : ids) {
			str.append("Bus(" + id + ")" + ",");
		}
		return str.indexOf(",") >= 0 ? str.toString().substring(0,
				str.length() - 1) : null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}

		Edge other = (Edge) obj;

		if (!source.equals(other.getSource())) {
			return false;
		}

		if (!destination.equals(other.getDestination())) {
			return false;
		}

		return true;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + source.hashCode();
		result = prime * result + destination.hashCode();
		result = prime * result + ids.hashCode();
		return result;
	}

}
