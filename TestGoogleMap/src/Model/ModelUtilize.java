package Model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;

public class ModelUtilize {

	/**
	 * @param args
	 */
	Context c;
	public static List<BusStop> listBusStop = new ArrayList<BusStop>();
	public static List<BusRoute> listBusRoute = new ArrayList<BusRoute>();
	public static final String FILE_LUOT_DI = "Routes_Luot_Di.txt";
	public static final String FILE_LUOT_VE = "Routes_Luot_Ve.txt";
	private static ModelUtilize instance = null;

	public static ModelUtilize getInstance(Context c) {
		if (instance == null) {
			instance = new ModelUtilize(c);
		}
		return instance;
	}

	protected ModelUtilize() {

	}

	private ModelUtilize(Context c) {
		// TODO Auto-generated constructor stub
		this.c = c;
	}

	public static void initiateModel() {

	}
	public static List<BusRoute> getBusRouteList(Context c){
		return getInstance(c).getBusRouteList();
	}
	public List<BusRoute> getBusRouteList() {
		if (listBusRoute.isEmpty()) {
			initiateBusRoute();
		}
		return listBusRoute;
	}

	public void initiateBusRoute() {
		// Read list bus route from file luotdi va luotve and return value to
		// listBusRoute
		AssetManager asset = c.getAssets();
		try {
			InputStream is = asset.open(FILE_LUOT_DI);
			DataInputStream dis = new DataInputStream(is);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				parseString(strLine);
			}
			dis.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseString(String strLine) {
		// parse from each line the number of bus. and list of bus stop.

		String afterSplit[] = strLine.split("[|]");
		BusRoute tmp = new BusRoute(Integer.parseInt(afterSplit[0]));
		for (int i = 1; i < afterSplit.length; i++) {
			tmp.add(new BusStop(afterSplit[i]));
		}
		listBusRoute.add(tmp);
	}

}
