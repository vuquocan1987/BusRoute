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
			bs.setLatitude(cursor.getFloat(1));
			bs.setLongitude(cursor.getFloat(2));
			bs.setDescription(cursor.getString(3));
			busStations.add(bs);
		}
		return busStations;
	}
}
