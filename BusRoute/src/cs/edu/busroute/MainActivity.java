package cs.edu.busroute;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

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
		if (servicesConnected()) {
			final Intent mapIntent = new Intent(MainActivity.this,
					MapActivity.class);
			startActivity(mapIntent);
		}
	}

	private boolean servicesConnected() {

		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// Continue
			return true;
			// Google Play services was not available for some reason
		} else {
			// Display an error dialog
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,
					this, 0);
			if (dialog != null) {
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(dialog);
				errorFragment.show(getFragmentManager(), "BusRoute");
			}
			return false;
		}
	}

	public static class ErrorDialogFragment extends DialogFragment {

		// Global field to contain the error dialog
		private Dialog mDialog;

		/**
		 * Default constructor. Sets the dialog field to null
		 */
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		/**
		 * Set the dialog to display
		 * 
		 * @param dialog
		 *            An error dialog
		 */
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		/*
		 * This method must return a Dialog to the DialogFragment.
		 */
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
