package cs.edu.busroute.model;

import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.graph.Graph;

/**
 * 
 * @author HoangNguyen
 * 
 */
public class BusGraph {
	private final Map<String, Station> mapStation;
	private final List<Edge> edges;
	private final Graph graph;

	public BusGraph(Map<String, Station> stations, List<Edge> edges, Graph graph) {
		this.mapStation = stations;
		this.edges = edges;
		this.graph = graph;
	}

	public Graph getGraph() {
		return graph;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public Map<String, Station> getMapStation() {
		return mapStation;
	}
}
