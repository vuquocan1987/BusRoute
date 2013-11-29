package cs.edu.busroute;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import cs.edu.busroute.db.service.impl.GraphBuilder;

public class LoadingActivity extends AbstractDataSourceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_loading);
		GraphBuilder.setBusGraph(getDataSource().buildGraph()); // run
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				final Intent mainIntent = new Intent(LoadingActivity.this,
						MainActivity.class);
				startActivity(mainIntent);
				finish();
			}
		}, 2000);

	}

}
