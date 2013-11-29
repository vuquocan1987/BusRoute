package cs.edu.busroute.db.service.impl;

import cs.edu.busroute.model.BusGraph;

/**
 * 
 * @author HoangNguyen
 * 
 */
public final class GraphBuilder {
	private static BusGraph graph;

	private GraphBuilder() {
		// to hide constructor
	}

	public static BusGraph getBusGraph() {
		return graph;
	}

	public static void setBusGraph(BusGraph graph) {
		GraphBuilder.graph = graph;
	}
}
