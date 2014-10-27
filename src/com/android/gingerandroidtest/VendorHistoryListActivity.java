package com.android.gingerandroidtest;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class VendorHistoryListActivity extends Activity{

	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendor_history_list);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		TextView vendorNameView = (TextView) findViewById(R.id.vendor_name_text);
		Intent intent = getIntent();
		String vendorName = intent.getStringExtra(Utils.extraVendorName);
		vendorNameView.setText(vendorName);
		ListView list = (ListView) findViewById(R.id.vendorHistoryList);

		VendorData vendor = Utils.vendorMap.get(vendorName);
		ArrayList<String> values = new ArrayList<String>();
		for(Date date : vendor.getPastEvents()){
			values.add(date.toString());
		}

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,values);

		list.setAdapter(adapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch(item.getItemId()){
		case android.R.id.home :
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}





}
