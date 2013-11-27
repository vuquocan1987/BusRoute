package cs.edu.busroute;

import android.app.Activity;
import cs.edu.busroute.db.service.BusDataSource;
import cs.edu.busroute.db.service.impl.BusDataSourceImpl;

public abstract class AbstractDataSourceActivity extends Activity {
	private BusDataSource dataSource;

	public BusDataSource getDataSource() {
		if (dataSource == null) {
			dataSource = new BusDataSourceImpl(this);
		}
		dataSource.open();
		return dataSource;
	}
}
