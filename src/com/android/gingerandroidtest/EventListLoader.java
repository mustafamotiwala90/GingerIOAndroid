package com.android.gingerandroidtest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.AsyncTaskLoader;

class EventListLoader extends AsyncTaskLoader<List<EventData>> {

	public static String eventsUrl = "EVENTS_URL";
	public static String start_time = "start_time";
	public static String end_time = "end_time";

	public String url;
	public List<EventData> result = new ArrayList<EventData>();
	public HashMap<String, EventData> listFoodTrucks = new HashMap<String, EventData>();
	public HashMap<String, EventData> eventDaysTrucks = new HashMap<String,EventData>();

	public EventListLoader(String url) {
		super(GingerIOApp.getContext());
		this.url = url;
	}

	@Override
	public List<EventData> loadInBackground() {
		int count = 0;

		while (count < 10) {
			if(url!=null)
				url = fetchDataFromUrl(url);
			else
				break;
			count++;
		}
		return result;
	}

	public String fetchDataFromUrl(String dataUrl) {
		HttpClient client = new DefaultHttpClient();
		ResponseHandler<String> response = new BasicResponseHandler();
		HttpGet request = new HttpGet(dataUrl);
		String responseBody = "";
		String nexturl = "";
		try {
			responseBody = client.execute(request, response);
			nexturl = parseJsonFromString(responseBody);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return nexturl;
	}

	public String parseJsonFromString(String response) throws JSONException {
		JSONObject jsonObj = new JSONObject(response);
		JSONArray dataArr = jsonObj.getJSONArray("data");
		for (int i = 0; i < dataArr.length(); i++) {
			JSONObject obj = dataArr.getJSONObject(i);
			String eventName = Utils.getEventName(obj)[0];
			EventData event;
			if (listFoodTrucks.containsKey(eventName)) {
				event = listFoodTrucks.get(eventName);
				event.set_start_time(obj.getString(start_time));
				event.set_end_time(obj.getString(end_time));
			} else {
				event = new EventData(obj);
				event.set_start_time(obj.getString(start_time));
				event.set_end_time(obj.getString(end_time));
				listFoodTrucks.put(event.getEventName(), event);
				result.add(event);
				Utils.idListVendors.add(event.getId());
			}
		}

		String nextUrl = jsonObj.getJSONObject("paging").getString("next");
		System.out.println("Next URl :" + nextUrl);
		return nextUrl;
	}

	@Override
	public void deliverResult(List<EventData> data) {
		super.deliverResult(data);
	}

}
