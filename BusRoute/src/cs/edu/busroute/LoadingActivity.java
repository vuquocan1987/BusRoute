package cs.edu.busroute;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class LoadingActivity extends AbstractDataSourceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_loading);
		getDataSource().open(); // copy database at the first run
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				final Intent mainIntent = new Intent(LoadingActivity.this,
						MainActivity.class);
				startActivity(mainIntent);
				finish();
			}
		}, 3000);
	}
}
