package com.android.gingerandroidtest;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class upcomingEventsListAdapter extends ArrayAdapter<EventData>{

	public upcomingEventsListAdapter(Context context, List<EventData> data){
		super(context.getApplicationContext(), 0, data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = View.inflate(parent.getContext(), R.layout.event_list_item, null);
			view.setTag(new ViewHolder(view));
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		EventData event = getItem(position);

		holder.nameView.setText(event.getEventName() + "("+event.getId()+")");

		return view;
	}

	private class ViewHolder {
		final TextView nameView;

		ViewHolder(View view) {
			nameView = (TextView) view.findViewById(R.id.name_text);
		}
	}

}
