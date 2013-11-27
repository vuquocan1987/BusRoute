package cs.edu.busroute;

import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AbstractDataSourceActivity {

	private AutoCompleteTextView txt_startPoint;
	private AutoCompleteTextView txt_endPoint;
	private String startPoint, endPoint;
	private Map<String, LatLng> mapStations;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txt_startPoint = (AutoCompleteTextView) findViewById(R.id.txt_startPoint);
		txt_endPoint = (AutoCompleteTextView) findViewById(R.id.txt_endPoint);
		mapStations = getDataSource().getAllStation();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mapStations.keySet()
						.toArray(new String[] {}));
		txt_startPoint.setAdapter(adapter);
		txt_endPoint.setAdapter(adapter);

	}

	public void onClick(View view) {
		txt_startPoint.setError(null);
		txt_endPoint.setError(null);
		startPoint = txt_startPoint.getText().toString();
		endPoint = txt_endPoint.getText().toString();
		// TODO: hanlde error here
		boolean cancel = false;

		if (TextUtils.isEmpty(startPoint)) {
			txt_startPoint.setError(getString(R.string.error_startPoint));
			cancel = true;
		}

		if (TextUtils.isEmpty(endPoint)) {
			txt_endPoint.setError(getString(R.string.error_endPoint));
			cancel = true;
		}
		if (!cancel) {
			final Intent intent = new Intent(MainActivity.this,
					BusRouteActivity.class);
			Bundle source = new Bundle();
			double[] latlng1 = { mapStations.get(startPoint).latitude,
					mapStations.get(startPoint).longitude };
			source.putDoubleArray(startPoint, latlng1);

			Bundle target = new Bundle();
			double[] latlng2 = { mapStations.get(endPoint).latitude,
					mapStations.get(endPoint).longitude };
			target.putDoubleArray(endPoint, latlng2);

			intent.putExtra("source", source);
			intent.putExtra("target", target);
			startActivity(intent);
			finish();
		}
	}

	public void openMap(View view) {
		if (servicesConnected()) {
			// TODO: write a function to ask people turn on GPS on their device
			// before starting map activity.
			final Intent mapIntent = new Intent(MainActivity.this,
					MapActivity.class);
			startActivity(mapIntent);
		}
	}

	private boolean enableGPSIfPossible() {
		final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			buildAlertMessageNoGps();
		}
		return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Your GPS seems to be disabled, do you want to enable it?")
				.setCancelable(false);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(
						Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		final AlertDialog alert = builder.create();
		alert.show();
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
