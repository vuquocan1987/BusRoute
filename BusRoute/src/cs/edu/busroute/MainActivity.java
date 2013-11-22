package cs.edu.busroute;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import cs.edu.busroute.db.helper.BusInfoHelper;

public class MainActivity extends Activity {

	private BusInfoHelper busInfoHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// create our database, should be use
		busInfoHelper = new BusInfoHelper(this);
		busInfoHelper.createDatabase();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
