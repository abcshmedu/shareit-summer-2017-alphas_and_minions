package data;

import java.util.ArrayList;

import org.json.JSONObject;

/**
 * Simple Generic database to save JSONobj.
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
public class JsonDatabase {

	ArrayList<JSONObject> datastorage;
	
	public JsonDatabase() {
		this.datastorage = new ArrayList<JSONObject>();
	}

	/**
	 * @return the datastorage
	 */
	public ArrayList<JSONObject> getDatastorage() {
		return datastorage;
	}

	/**
	 * @param datastorage the datastorage to set
	 */
	public void setDatastorage(ArrayList<JSONObject> datastorage) {
		this.datastorage = datastorage;
	}
	
	
}
