package com.android.gingerandroidtest;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VendorListActivity extends ListActivity implements LoaderCallbacks<ArrayList<String>>{

	public String vendorUrl = "https://graph.facebook.com/";
	public static String paramsUrl = "?fields=description&access_token=";
	private LoaderManager.LoaderCallbacks<ArrayList<String>> mCallbacks;
	private ArrayList<String> values = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this,VendorHistoryListActivity.class);
		intent.putExtra(Utils.extraVendorName,values.get(position));
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendor_list);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		String ID = intent.getStringExtra(upcomingEventsFragment.EXTRA_ID);
		vendorUrl = vendorUrl + ID + paramsUrl + getString(R.string.access_token);
		Utils.addToIdMap(ID);
		mCallbacks = VendorListActivity.this;
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		LoaderManager lm = getLoaderManager();
		Loader<ArrayList<String>> loader = lm.initLoader(LoaderId.VendorListLoaderId, null, mCallbacks);
		loader.forceLoad();

	}

	@Override
	public Loader<ArrayList<String>> onCreateLoader(int id, Bundle args) {
		return new VendorListLoader(vendorUrl);
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<String>> loader,
			ArrayList<String> data) {
		values.addAll(data);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<String>> loader) {
		values.clear();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
