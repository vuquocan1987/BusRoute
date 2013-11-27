package cs.edu.busroute;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText txt_startPoint;
	private EditText txt_endPoint;
	private String startPoint, endPoint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txt_startPoint = (EditText) findViewById(R.id.txt_startpoint);
		txt_endPoint = (EditText) findViewById(R.id.txt_endpoint);

	}

	public void onClick(View view) {
		txt_startPoint.setError(null);
		txt_endPoint.setError(null);
		startPoint = txt_startPoint.getText().toString();
		endPoint = txt_endPoint.getText().toString();
		boolean cancel = false;

		if (TextUtils.isEmpty(startPoint)) {
			txt_startPoint.setError(getString(R.string.error_startPoint));
			cancel = true;
		}

		if (TextUtils.isEmpty(endPoint)) {
			txt_endPoint.setError(getString(R.string.error_endPoint));
			cancel = true;
		}
	}

	public void openMap(View view) {
		final Intent mapIntent = new Intent(MainActivity.this,
				MapActivity.class);
		startActivity(mapIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
