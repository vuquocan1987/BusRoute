package cs.edu.busroute.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * 
 * @author HoangNguyen
 * 
 */
public class Station {

	private LatLng stationGPS;
	private String description;

	public String getDescription() {
		return description;
	}

	public LatLng getStationGPS() {
		return stationGPS;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStationGPS(LatLng stationGPS) {
		this.stationGPS = stationGPS;
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

		Station other = (Station) obj;

		if (stationGPS.latitude != other.getStationGPS().latitude) {
			return false;
		}

		if (stationGPS.longitude != other.getStationGPS().longitude) {
			return false;
		}

		return true;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + stationGPS.hashCode();
		result = prime * result + description.hashCode();
		return result;
	}
}
