package Model;

import com.google.android.gms.maps.model.LatLng;

public class BusStop {
	LatLng busStopGPS;
	String name;

	public BusStop(String string) {
		// TODO Auto-generated constructor stub
		String splittedBusInfo[] = string.split("[;]");
		double x = Double.parseDouble(splittedBusInfo[0]);
		double y = Double.parseDouble(splittedBusInfo[1]);
		busStopGPS = new LatLng(x, y);
		name = splittedBusInfo[2];
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return busStopGPS.latitude + " " + busStopGPS.longitude + " " + name;
	}
}
