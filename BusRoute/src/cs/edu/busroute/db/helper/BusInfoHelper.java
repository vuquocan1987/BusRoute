package cs.edu.busroute.db.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * An utility class for manipulating with bus's database
 * 
 * @author HoangNguyen
 * 
 */
public class BusInfoHelper extends SQLiteOpenHelper {

	private static final String DATABASE_PATH = "/data/data/cs.edu.busroute/databases/";
	private static final String DATABASE_NAME = "busdata.db";
	private static final int SCHEMA_VERSION = 1;

	public static final String TABLE_NAME = "BusStation";

	public static final String COLUMN_ID = "id";

	public static final String COLUMN_LATITUDE = "latitude";

	public static final String COLUMN_LONGITUDE = "longitude";

	public static final String COLUMN_DESC = "description";

	public SQLiteDatabase dbSqlite;

	private final Context myContext;

	public BusInfoHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
		this.myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

	public void createDatabase() {
		boolean dbExist = isDbExists();
		if (!dbExist) {
			this.getReadableDatabase();
			copyDBFromResource();
		}
	}

	private boolean isDbExists() {
		SQLiteDatabase db = null;

		try {
			String databasePath = DATABASE_PATH + DATABASE_NAME;
			db = SQLiteDatabase.openDatabase(databasePath, null,
					SQLiteDatabase.OPEN_READONLY);
			// db.setLocale(Locale.getDefault());
			// db.setLockingEnabled(true);
			// db.setVersion(1);
		} catch (SQLiteException e) {
			Log.e("SqlHelper", "database not found");
		}

		if (db != null) {
			db.close();
		}
		return db != null;
	}

	private void copyDBFromResource() {

		InputStream inputStream = null;
		OutputStream outStream = null;
		String dbFilePath = DATABASE_PATH + DATABASE_NAME;

		try {
			inputStream = myContext.getAssets().open(DATABASE_NAME);
			outStream = new FileOutputStream(dbFilePath);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}

			outStream.flush();
			outStream.close();
			inputStream.close();
		} catch (IOException e) {
			throw new Error("Problem copying database from resource file.");
		}
	}
}
