package cs.edu.busroute.model;

/**
 * Model class for bus station. This class is mapped to BusStation table
 * 
 * @author HoangNguyen
 * 
 */
public class BusStation {
	private long id;
	private double latitude;
	private double longitude;
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
