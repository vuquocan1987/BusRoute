package cs.edu.busroute.model;

import java.util.LinkedList;

/**
 * A model for bus route. Provide some methods for checking or finding the path
 * between two stations.
 * 
 * @author HoangNguyen
 * 
 */
public class BusRoute {
	private long id;
	private final LinkedList<Station> stationForward = new LinkedList<Station>();

	private final LinkedList<Station> stationBackward = new LinkedList<Station>();

	public long getId() {
		return id;
	}

	public LinkedList<Station> getStationBackward() {
		return stationBackward;
	}

	public LinkedList<Station> getStationForward() {
		return stationForward;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Check for the path between two Stations.
	 * 
	 * @param source
	 * @param dest
	 * @return True/False
	 */
	public boolean hasPathForTwoStation(Station source, Station dest) {
		return findInFw(source, dest) || findInBw(source, dest);
	}

	/**
	 * Find the path between two Stations.
	 * 
	 * @param source
	 * @param dest
	 * @return list of Stations.
	 */
	public LinkedList<Station> getPathBetweenTwoStation(Station source,
			Station dest) {
		// TODO
		return null;
	}

	private boolean findInFw(Station source, Station dest) {
		if (!stationForward.contains(source) || !stationForward.contains(dest)) {
			return false;
		} else {
			return stationForward.indexOf(source) < stationForward
					.indexOf(dest);
		}
	}

	private boolean findInBw(Station source, Station dest) {
		if (!stationBackward.contains(source)
				|| !stationBackward.contains(dest)) {
			return false;
		} else {
			return stationBackward.indexOf(source) < stationBackward
					.indexOf(dest);
		}
	}
}
