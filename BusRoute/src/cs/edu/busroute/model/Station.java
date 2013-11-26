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
}
