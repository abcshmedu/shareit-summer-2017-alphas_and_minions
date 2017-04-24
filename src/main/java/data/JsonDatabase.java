package data;

import java.util.ArrayList;

import org.json.JSONObject;

/**
 * Simple Generic database to save JSONobj.
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
public class JsonDatabase {

	ArrayList<JSONObject> datastorageBooks;
	ArrayList<JSONObject> datastorageDiscs;
	
	public JsonDatabase() {
		this.datastorageBooks = new ArrayList<JSONObject>();
		this.datastorageDiscs = new ArrayList<JSONObject>();
	}

	/**
	 * @return the datastorageBooks
	 */
	public ArrayList<JSONObject> getDatastorageBooks() {
		return datastorageBooks;
	}

	/**
	 * @param datastorageBooks the datastorageBooks to set
	 */
	public void setDatastorageBooks(ArrayList<JSONObject> datastorageBooks) {
		this.datastorageBooks = datastorageBooks;
	}

	/**
	 * @return the datastorageDiscs
	 */
	public ArrayList<JSONObject> getDatastorageDiscs() {
		return datastorageDiscs;
	}

	/**
	 * @param datastorageDiscs the datastorageDiscs to set
	 */
	public void setDatastorageDiscs(ArrayList<JSONObject> datastorageDiscs) {
		this.datastorageDiscs = datastorageDiscs;
	}
	
	
}
