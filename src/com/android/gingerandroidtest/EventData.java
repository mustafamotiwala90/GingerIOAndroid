package com.android.gingerandroidtest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class EventData {

	private String eventName;
	private String address;
	private List<String> start_time;
	private List<String> end_time;
	private String id;


	public EventData(JSONObject jsonObject) throws JSONException{
		String[] eventDetails = Utils.getEventName(jsonObject);
		eventName = eventDetails[0];
		address = jsonObject.getString("location");
		id = jsonObject.getString("id");
		start_time = new ArrayList<String>();
		end_time = new ArrayList<String>();
	}


	public String getEventName() {
		return eventName;
	}

	public String getAddress() {
		return address;
	}

	public String getId() {
		return id;
	}

	public List<String> getStart_time() {
		return start_time;
	}

	public List<String> getEnd_time() {
		return end_time;
	}

	public void set_start_time(String start){
		start_time.add(start);
	}

	public void set_end_time(String end){
		end_time.add(end);
	}

}
