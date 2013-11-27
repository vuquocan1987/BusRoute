package cs.edu.busroute;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BusRouteActivity extends Activity {
	private GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		Bundle source = getIntent().getBundleExtra("source");
		Bundle target = getIntent().getBundleExtra("target");
		try {
			initilizeMap();

			addMarker(source);
			addMarker(target);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addMarker(Bundle source) {
		String description = source.keySet().toArray(new String[0])[0];
		double[] latlng = source.getDoubleArray(description);
		// create marker
		MarkerOptions marker = new MarkerOptions().position(
				new LatLng(latlng[0], latlng[1])).title(description);

		// adding marker
		googleMap.addMarker(marker);
	}

	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map_fragment)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}
