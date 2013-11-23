package Model;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class BusRoute {
	int busRouteNumber;
	ArrayList<BusStop> busStopList = new ArrayList<BusStop>();

	public BusRoute(int busRouteNumber) {
		this.busRouteNumber = busRouteNumber;
	}

	public void add(BusStop b) {
		busStopList.add(b);
	}
	public List<LatLng> getGPSList(){
		ArrayList <LatLng> results = new ArrayList<LatLng>();
		for (BusStop bus_stop : busStopList) {
			results.add(bus_stop.busStopGPS);
		}
		return results;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String result = "";
		for (BusStop bus_stop : busStopList) {
			result += bus_stop.toString() + "\n";
		}
		return result;
	}
}
