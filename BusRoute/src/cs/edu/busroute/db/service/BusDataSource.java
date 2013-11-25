package cs.edu.busroute.db.service;

import java.util.List;

import android.database.SQLException;
import cs.edu.busroute.model.BusStation;

public interface BusDataSource {
	public void open() throws SQLException;

	public void close();

	public List<BusStation> getBusStationById(long id);

	public List<BusStation> getAllBusStation();

	public List<Long> getBusIdListForStation(double latitude, double longtitude);
}
