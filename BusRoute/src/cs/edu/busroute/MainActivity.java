package cs.edu.busroute;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import cs.edu.busroute.db.dao.BusDataSource;
import cs.edu.busroute.model.BusStation;

public class MainActivity extends Activity {

	private BusDataSource dataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// create our database, should be use
		dataSource = new BusDataSource(this);
		dataSource.open();

		List<BusStation> busStations = dataSource.getBusStationById(1);
		System.out.print(busStations);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
