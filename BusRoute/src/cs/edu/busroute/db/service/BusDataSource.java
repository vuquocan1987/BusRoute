package cs.edu.busroute.db.service;

import java.util.List;
import java.util.Map;

import android.database.SQLException;

import com.google.android.gms.maps.model.LatLng;

import cs.edu.busroute.db.helper.TableTypeEnum;
import cs.edu.busroute.model.BusStation;

public interface BusDataSource {
	public void open() throws SQLException;

	public void close();

	public List<BusStation> getBusStationById(long id, TableTypeEnum tableType);

	public List<BusStation> getAllBusStation(TableTypeEnum tableType);

	public List<Long> getBusIdListForStation(LatLng stationGPS,
			TableTypeEnum tableType);

	public Map<String, LatLng> getAllStation();
}
