package cs.edu.busroute;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.Transformer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import cs.edu.busroute.db.service.impl.GraphBuilder;
import cs.edu.busroute.model.Edge;
import cs.edu.busroute.model.Station;
import cs.edu.busroute.task.GMapV2Direction.DirectionReceivedListener;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;

public class BusRouteActivity extends Activity implements
		DirectionReceivedListener {
	private GoogleMap googleMap;
	private Map<String, Station> mapStation;
	LatLng startPosition;
	LatLng destinationPosition;
	List<Edge> edges;
	StringBuilder routeInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		Bundle source = getIntent().getBundleExtra("source");
		Bundle target = getIntent().getBundleExtra("target");
		try {
			initilizeMap();

			Station src = bundleToStation(source);
			Station dest = bundleToStation(target);

			startPosition = src.getStationGPS();
			destinationPosition = dest.getStationGPS();

			Transformer<Edge, Double> wtTransformer = new Transformer<Edge, Double>() {
				@Override
				public Double transform(Edge edge) {
					return edge.getWeight();
				}
			};
			@SuppressWarnings("unchecked")
			DijkstraShortestPath<Station, Edge> alg = new DijkstraShortestPath<Station, Edge>(
					GraphBuilder.getBusGraph().getGraph(), wtTransformer);
			mapStation = GraphBuilder.getBusGraph().getMapStation();
			edges = alg.getPath(mapStation.get(src.getDescription()),
					mapStation.get(dest.getDescription()));

			showBusRouteByEdge();
			addMarker(source);
			addMarker(target);

			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(src.getStationGPS()).zoom(14).build();

			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showBusRouteByEdge() {
		routeInfo = new StringBuilder();
		for (Edge edge : edges) {

			routeInfo.append(
					"From Station[" + edge.getSource() + "] to Station["
							+ edge.getDestination() + "]: "
							+ edge.printListBusId()).append("\n");

			List<Station> stations = new ArrayList<Station>();
			stations.add(mapStation.get(edge.getSource()));
			stations.add(mapStation.get(edge.getDestination()));
			showBusRoute(stations);

			addMarkerFromStation(mapStation.get(edge.getSource()));
			addMarkerFromStation(mapStation.get(edge.getDestination()));

			/*
			 * new GetRouteListTask(BusRouteActivity.this, mapStation.get(
			 * edge.getSource()).getStationGPS(), mapStation.get(
			 * edge.getDestination()).getStationGPS(),
			 * GMapV2Direction.MODE_DRIVING, this).execute();
			 */

		}
	}

	private void showBusRoute(List<Station> stations) {
		List<LatLng> busGPSList = new LinkedList<LatLng>();
		for (Station station : stations) {
			busGPSList.add(station.getStationGPS());
		}
		googleMap.addPolyline(new PolylineOptions().addAll(busGPSList)
				.color(Color.BLACK).width(5));
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(busGPSList.get(0)));
	}

	private Station bundleToStation(Bundle bundle) {
		Station station = new Station();
		String description = bundle.keySet().toArray(new String[0])[0];
		double[] latlng = bundle.getDoubleArray(description);
		station.setDescription(description);
		station.setStationGPS(new LatLng(latlng[0], latlng[1]));
		return station;
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

	private void addMarkerFromStation(Station station) {
		MarkerOptions marker = new MarkerOptions().position(
				station.getStationGPS()).title(station.getDescription());
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

	@Override
	public void OnDirectionListReceived(List<LatLng> mPointList) {
		if (mPointList != null) {
			PolylineOptions rectLine = new PolylineOptions().width(10).color(
					Color.CYAN);
			for (int i = 0; i < mPointList.size(); i++) {
				rectLine.add(mPointList.get(i));
			}
			googleMap.addPolyline(rectLine);

			/*
			 * CameraPosition mCPFrom = new CameraPosition.Builder()
			 * .target(startPosition).zoom(14).bearing(0).tilt(25).build();
			 * final CameraPosition mCPTo = new CameraPosition.Builder()
			 * .target(destinationPosition).zoom(14).bearing(0).tilt(50)
			 * .build();
			 * 
			 * changeCamera(CameraUpdateFactory.newCameraPosition(mCPFrom), new
			 * CancelableCallback() {
			 * 
			 * @Override public void onFinish() {
			 * changeCamera(CameraUpdateFactory .newCameraPosition(mCPTo), new
			 * CancelableCallback() {
			 * 
			 * @Override public void onFinish() {
			 * 
			 * LatLngBounds bounds = new LatLngBounds.Builder()
			 * .include(startPosition) .include( destinationPosition) .build();
			 * changeCamera( CameraUpdateFactory .newLatLngBounds( bounds, 50),
			 * null, false); }
			 * 
			 * @Override public void onCancel() { } }, false); }
			 * 
			 * @Override public void onCancel() { } }, true);
			 */
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.menuDetail:
			openDialogInformation();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void openDialogInformation() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// set title
		alertDialogBuilder.setTitle("Route Information");
		// set dialog message
		alertDialogBuilder.setMessage(routeInfo.toString())
				.setCancelable(false)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

}
