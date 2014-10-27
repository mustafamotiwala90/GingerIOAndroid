package com.android.gingerandroidtest;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.content.AsyncTaskLoader;

public class VendorListLoader extends AsyncTaskLoader<ArrayList<String>> {

	public String url;


	public VendorListLoader(String url) {
		super(GingerIOApp.getContext());
		this.url = url;
	}

	@Override
	public ArrayList<String> loadInBackground() {
		return fetchDataFromUrl(url);
	}

	public ArrayList<String> fetchDataFromUrl(String dataUrl) {
		HttpClient client = new DefaultHttpClient();
		ResponseHandler<String> response = new BasicResponseHandler();
		HttpGet request = new HttpGet(dataUrl);
		String responseBody = "";
		ArrayList<String> vendorName = new ArrayList<String>();
		try {
			responseBody = client.execute(request, response);
			vendorName = Utils.parseJsonFromString(responseBody);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return vendorName;
	}
}
