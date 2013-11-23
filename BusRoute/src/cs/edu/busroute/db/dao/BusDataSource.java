package cs.edu.busroute.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cs.edu.busroute.db.helper.BusInfoHelper;
import cs.edu.busroute.model.BusStation;

/**
 * @author HoangNguyen
 * 
 */
public class BusDataSource {
	private static final int COL_INDEX_3 = 3;
	private static final int COL_INDEX_2 = 2;
	private static final int COL_INDEX_1 = 1;
	private SQLiteDatabase database;
	private final BusInfoHelper dbHelper;

	public BusDataSource(Context context) {
		dbHelper = new BusInfoHelper(context);
	}

	public void open() throws SQLException {
		dbHelper.createDatabase();
		database = dbHelper.getReadableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * Get the stations for given bus id
	 * 
	 * @param id
	 * @return list of bus station
	 */
	public List<BusStation> getBusStationById(long id) {
		List<BusStation> busStations = new ArrayList<BusStation>();
		String query = "Select * from " + BusInfoHelper.TABLE_NAME
				+ " where id = ? ";
		Cursor cursor = database.rawQuery(query,
				new String[] { String.valueOf(id) });
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			BusStation bs = new BusStation();
			bs.setId(id);
			bs.setLatitude(cursor.getFloat(COL_INDEX_1));
			bs.setLongitude(cursor.getFloat(COL_INDEX_2));
			bs.setDescription(cursor.getString(COL_INDEX_3));
			busStations.add(bs);
		}
		return busStations;
	}
}
