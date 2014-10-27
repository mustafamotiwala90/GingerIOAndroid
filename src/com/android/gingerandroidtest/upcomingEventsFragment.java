package com.android.gingerandroidtest;

import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class upcomingEventsFragment extends Fragment implements OnItemClickListener{

	public static List<EventData> eventsData;
	public static String EXTRA_ID = "EXTRA_ID";

	public static upcomingEventsFragment newInstance(List<EventData> result){
		eventsData=result;
		return new upcomingEventsFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.upcoming_events, container,false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);

		final ViewHolder holder = new ViewHolder(view);
		view.setTag(holder);

		upcomingEventsListAdapter adapter = new upcomingEventsListAdapter(view.getContext(),eventsData);
		holder.listView.setAdapter(adapter);
		holder.listView.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(getActivity(), VendorListActivity.class);
		String message = eventsData.get(position).getId();
		intent.putExtra(EXTRA_ID, message);
		startActivity(intent);
	}


	private class ViewHolder {
		final ListView listView;

		ViewHolder(View view) {
			listView = (ListView) view.findViewById(R.id.listEvents);
		}
	}





}