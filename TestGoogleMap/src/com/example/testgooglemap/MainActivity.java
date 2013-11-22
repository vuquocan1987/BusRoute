package com.example.testgooglemap;

import java.util.ArrayList;
import java.util.List;

import Model.BusRoute;
import Model.ModelUtilize;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends Activity implements OnClickListener {
	private GoogleMap map;
	static final LatLng bus1 = new LatLng(10.7767894851893, 106.70585699056292);
	static final LatLng bus2 = new LatLng(10.773223266971607,
			106.70639541003383);
	public static int busRouteNumber = 5;
	List<BusRoute> myBusRouteList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		if (map != null) {
			Marker firstBus = map.addMarker(new MarkerOptions().position(bus1)
					.title("first bus"));
			Marker secondBus = map.addMarker(new MarkerOptions()
					.position(bus2)
					.title("Mighty Bus")
					.snippet("The bus android bus is soooo coooolllll")
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.ic_launcher)));
		}
		Button btnInitiateData = (Button) findViewById(R.id.InitiateData);
		btnInitiateData.setOnClickListener(this);
		Button btnShowBusRoute = (Button) findViewById(R.id.ShowBusRoute);
		btnShowBusRoute.setOnClickListener(this);
		map.moveCamera(CameraUpdateFactory.zoomBy(10));
		map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(10.7767894851893,106.70585699056292)));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.InitiateData:
			myBusRouteList = ModelUtilize.getBusRouteList(this);
			Toast.makeText(this, myBusRouteList.get(10).toString(),
					Toast.LENGTH_LONG).show();
			break;
		case R.id.ShowBusRoute:
			showBusRoute(busRouteNumber++);
		default:
			break;
		}

	}

	private void showBusRoute(int busRouteNumber) {
		// TODO Auto-generated method stub
		List<LatLng> busGPSList = myBusRouteList.get(busRouteNumber)
				.getGPSList();
		map.addPolyline(new PolylineOptions().addAll(busGPSList)
				.color(Color.CYAN).width(5));
		map.moveCamera(CameraUpdateFactory.newLatLng(myBusRouteList.get(busRouteNumber).getGPSList().get(1)));
		
	}

}
