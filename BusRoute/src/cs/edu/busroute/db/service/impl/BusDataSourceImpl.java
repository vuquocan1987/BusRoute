package cs.edu.busroute.db.service.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

import cs.edu.busroute.db.helper.BusInfoHelper;
import cs.edu.busroute.db.helper.TableTypeEnum;
import cs.edu.busroute.db.service.BusDataSource;
import cs.edu.busroute.model.BusStation;

/**
 * @author HoangNguyen
 * 
 */
public class BusDataSourceImpl implements BusDataSource {
	private static final int COL_INDEX_0 = 0;
	private static final int COL_INDEX_3 = 3;
	private static final int COL_INDEX_2 = 2;
	private static final int COL_INDEX_1 = 1;
	private SQLiteDatabase database;
	private final BusInfoHelper dbHelper;

	public BusDataSourceImpl(Context context) {
		dbHelper = new BusInfoHelper(context);
	}

	@Override
	public void open() throws SQLException {
		dbHelper.createDatabase();
		database = dbHelper.getReadableDatabase();
	}

	@Override
	public void close() {
		dbHelper.close();
	}

	/**
	 * Get the stations for given bus id
	 * 
	 * @param id
	 * @return list of bus station
	 */
	@Override
	public List<BusStation> getBusStationById(long id, TableTypeEnum tableType) {
		List<BusStation> busStations = new ArrayList<BusStation>();
		String query = "Select * from " + tableType.getTableName()
				+ " where id = ? ";
		Cursor cursor = database.rawQuery(query,
				new String[] { String.valueOf(id) });
		cursorToBusStation(busStations, cursor);
		return busStations;
	}

	private void cursorToBusStation(List<BusStation> busStations, Cursor cursor) {
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			BusStation bs = new BusStation();
			bs.setId(cursor.getLong(COL_INDEX_0));
			bs.setStationGPS(new LatLng(cursor.getFloat(COL_INDEX_1), cursor
					.getFloat(COL_INDEX_2)));
			bs.setDescription(cursor.getString(COL_INDEX_3));
			busStations.add(bs);
			cursor.moveToNext();
		}
	}

	/**
	 * 
	 * @return list all of bus station
	 */
	@Override
	public List<BusStation> getAllBusStation(TableTypeEnum tableType) {
		List<BusStation> busStations = new ArrayList<BusStation>();
		String query = "Select distinct * from " + tableType.getTableName()
				+ " group by latitude, longitude ";
		Cursor cursor = database.rawQuery(query, null);
		cursorToBusStation(busStations, cursor);
		return busStations;
	}

	/**
	 * 
	 * @param latitude
	 * @param longtitude
	 * @return the list of busId that go through this station
	 */
	@Override
	public List<Long> getBusIdListForStation(LatLng stationGPS,
			TableTypeEnum tableType) {
		List<Long> ids = new ArrayList<Long>();
		String query = "Select distinct id from " + tableType.getTableName()
				+ " where latitude = ? and longitude = ? ";
		Cursor cursor = database.rawQuery(
				query,
				new String[] { String.valueOf(stationGPS.latitude),
						String.valueOf(stationGPS.longitude) });
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ids.add(cursor.getLong(COL_INDEX_0));
			cursor.moveToNext();
		}
		return null;
	}
}
