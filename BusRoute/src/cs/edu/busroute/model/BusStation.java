package cs.edu.busroute.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Model class for bus station. This class is mapped to BusStation table
 * 
 * @author HoangNguyen
 * 
 */
public class BusStation {
	private long id;
	private LatLng stationGPS;
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LatLng getStationGPS() {
		return stationGPS;
	}

	public void setStationGPS(LatLng stationGPS) {
		this.stationGPS = stationGPS;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
