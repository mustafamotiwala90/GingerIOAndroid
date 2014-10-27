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

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryEventsFragment extends Fragment implements
OnItemClickListener {

	historyVendorsListAdapter adapter;
	public String vendorUrl = "https://graph.facebook.com/";
	public String paramsUrl = "?fields=description&access_token=";
	public static String bundleExtra = "URL";
	ViewHolder holder;

	public static HistoryEventsFragment newInstance() {
		return new HistoryEventsFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.upcoming_events, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);

		holder = new ViewHolder(view);
		view.setTag(holder);

		adapter = new historyVendorsListAdapter(Utils.vendorMap.keySet());
		holder.listView.setAdapter(adapter);
		holder.listView.setOnItemClickListener(this);

		int size = Utils.idListVendors.size();
		System.out.println("Size is :" + size);
		iterateExecutor();
	}

	public void iterateExecutor() {
		for (String id : Utils.idListVendors) {
			if (!Utils.checkIfIdExists(id)) {
				new DownloadTask(id)
				.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
		}
	}

	private class ViewHolder {
		final ListView listView;

		ViewHolder(View view) {
			listView = (ListView) view.findViewById(R.id.listEvents);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(),
				VendorHistoryListActivity.class);
		TextView textview = (TextView) view;
		intent.putExtra(Utils.extraVendorName, textview.getText().toString());
		startActivity(intent);
	}

	class DownloadTask extends AsyncTask<String, Void, String> {

		String url;

		public DownloadTask(String urlToCall) {
			this.url = vendorUrl + urlToCall + paramsUrl
					+ getString(R.string.access_token);
		}

		@Override
		protected String doInBackground(String... params) {
			fetchDataFromUrl(url);
			return "null";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			adapter.swapItems();

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
				System.out.println("CLient protocol exception");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("IOException  exception");
			} catch (JSONException e) {
				e.printStackTrace();
				System.out.println(" JSONException exception");
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("ParseException exception");
			}
			return vendorName;
		}
	}
}
