package cs.edu.busroute.db.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

import cs.edu.busroute.db.helper.BusInfoHelper;
import cs.edu.busroute.db.helper.TableTypeEnum;
import cs.edu.busroute.db.service.BusDataSource;
import cs.edu.busroute.model.BusGraph;
import cs.edu.busroute.model.BusStation;
import cs.edu.busroute.model.Edge;
import cs.edu.busroute.model.Station;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;

/**
 * @author HoangNguyen
 * 
 */
public class BusDataSourceImpl implements BusDataSource {
	private static final int COL_INDEX_0 = 0;
	private static final int COL_INDEX_3 = 3;
	private static final int COL_INDEX_2 = 2;
	private static final int COL_INDEX_1 = 1;
	private SQLiteDatabase database;
	private final BusInfoHelper dbHelper;

	public BusDataSourceImpl(Context context) {
		dbHelper = new BusInfoHelper(context);
	}

	@Override
	public void open() throws SQLException {
		dbHelper.createDatabase();
		database = dbHelper.getReadableDatabase();
	}

	@Override
	public void close() {
		dbHelper.close();
	}

	/**
	 * Get the stations for given bus id
	 * 
	 * @param id
	 * @return list of bus station
	 */
	@Override
	public List<BusStation> getBusStationById(long id, TableTypeEnum tableType) {
		List<BusStation> busStations = new LinkedList<BusStation>();
		String query = "Select * from " + tableType.getTableName()
				+ " where id = ? ";
		Cursor cursor = database.rawQuery(query,
				new String[] { String.valueOf(id) });
		cursorToBusStation(busStations, cursor);
		return busStations;
	}

	private void cursorToBusStation(List<BusStation> busStations, Cursor cursor) {
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			BusStation bs = new BusStation();
			bs.setId(cursor.getLong(COL_INDEX_0));
			bs.setStationGPS(new LatLng(cursor.getFloat(COL_INDEX_1), cursor
					.getFloat(COL_INDEX_2)));
			bs.setDescription(cursor.getString(COL_INDEX_3));
			busStations.add(bs);
			cursor.moveToNext();
		}
	}

	/**
	 * 
	 * @return list all of bus station
	 */
	@Override
	public List<BusStation> getAllBusStation(TableTypeEnum tableType) {
		List<BusStation> busStations = new ArrayList<BusStation>();
		String query = "Select distinct * from " + tableType.getTableName()
				+ " group by latitude, longitude ";
		Cursor cursor = database.rawQuery(query, null);
		cursorToBusStation(busStations, cursor);
		return busStations;
	}

	@Override
	public Map<String, LatLng> getAllStation() {
		Map<String, LatLng> map = new HashMap<String, LatLng>();

		getAllStationInternal(map, TableTypeEnum.FORWARD);
		getAllStationInternal(map, TableTypeEnum.BACKWARD);

		return map;
	}

	private void getAllStationInternal(Map<String, LatLng> map,
			TableTypeEnum tableType) {
		String query = "Select distinct * from " + tableType.getTableName()
				+ " group by latitude, longitude ";
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String description = cursor.getString(COL_INDEX_3);
			if (!map.values().contains(description)) {
				map.put(description, new LatLng(cursor.getFloat(COL_INDEX_1),
						cursor.getFloat(COL_INDEX_2)));
			}
			cursor.moveToNext();
		}
	}

	/**
	 * 
	 * @param latitude
	 * @param longtitude
	 * @return the list of busId that go through this station
	 */
	@Override
	public List<Long> getBusIdListForStation(LatLng stationGPS,
			TableTypeEnum tableType) {
		List<Long> ids = new ArrayList<Long>();
		String query = "Select distinct id from " + tableType.getTableName()
				+ " where latitude = ? and longitude = ? ";
		Cursor cursor = database.rawQuery(
				query,
				new String[] { String.valueOf(stationGPS.latitude),
						String.valueOf(stationGPS.longitude) });
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ids.add(cursor.getLong(COL_INDEX_0));
			cursor.moveToNext();
		}
		return null;
	}

	private double calculationByDistance(LatLng startPoint, LatLng endPoint) {
		int Radius = 6371;// radius of earth in Km
		double dLat = Math.toRadians(endPoint.latitude - startPoint.latitude);
		double dLon = Math.toRadians(endPoint.longitude - endPoint.longitude);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(startPoint.latitude))
				* Math.cos(Math.toRadians(endPoint.latitude))
				* Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return Radius * c;
	}

	@Override
	public BusGraph buildGraph() {
		Map<String, Station> stations = new HashMap<String, Station>();
		List<Edge> edges = new ArrayList<Edge>();
		for (int i = 1; i <= 152; i++) {
			buildGraphInternal(stations, edges, i, TableTypeEnum.FORWARD);
			buildGraphInternal(stations, edges, i, TableTypeEnum.BACKWARD);
		}
		Graph<Station, Edge> graph = new DirectedSparseMultigraph<Station, Edge>();
		for (Edge edge : edges) {
			if (stations.get(edge.getSource()) == null
					|| stations.get(edge.getDestination()) == null) {
				System.out.print(edge.toString());
			}
			graph.addEdge(edge, stations.get(edge.getSource()),
					stations.get(edge.getDestination()));
		}
		return new BusGraph(stations, edges, graph);
	}

	private void buildGraphInternal(Map<String, Station> stations,
			List<Edge> edges, int i, TableTypeEnum tableType) {
		List<BusStation> busStations = getBusStationById(i, tableType);
		if (!busStations.isEmpty()) {
			for (int j = 0; j < busStations.size() - 1; j++) {
				BusStation busSource = busStations.get(j);
				Station stationSrc = new Station();
				stationSrc.setDescription(busSource.getDescription());
				stationSrc.setStationGPS(busSource.getStationGPS());
				stationSrc.getIds().add(busSource.getId());

				addStation(stations, busSource, stationSrc);

				BusStation busDest = busStations.get(j + 1);
				Station stationDest = new Station();
				stationDest.setDescription(busDest.getDescription());
				stationDest.setStationGPS(busDest.getStationGPS());
				stationDest.getIds().add(busDest.getId());
				if (j == (busStations.size() - 2)) {
					addStation(stations, busDest, stationDest);
				}
				double weight = calculationByDistance(
						stationSrc.getStationGPS(), stationDest.getStationGPS());
				Edge edge = new Edge(stationSrc.getDescription(),
						stationDest.getDescription(), weight);
				if (!edges.contains(edge)) {
					edge.getIds().add(Long.valueOf(i));
					edges.add(edge);
				} else {
					for (Edge e : edges) {
						if (e.equals(edge)) {
							e.getIds().add(Long.valueOf(i));
						}
					}
				}
			}
		}
	}

	private void addStation(Map<String, Station> stations,
			BusStation busSource, Station stationSrc) {

		if (stations.get(stationSrc.getDescription()) == null) {
			stations.put(stationSrc.getDescription(), stationSrc);
		} else {
			stations.get(stationSrc.getDescription()).getIds()
					.add(busSource.getId());
		}
	}
}
