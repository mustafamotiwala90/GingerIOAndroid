package com.android.gingerandroidtest;

import java.util.ArrayList;
import java.util.Set;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class historyVendorsListAdapter extends BaseAdapter {

	ArrayList<String> items = new ArrayList<String>();

	public historyVendorsListAdapter(Set<String> values) {
		this.items.addAll(values);
	}

	public void swapItems() {
		items.clear();
		items.addAll(Utils.vendorMap.keySet());
		// Utils.sortVendorMap();
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = View.inflate(parent.getContext(), R.layout.event_list_item,
					null);
			view.setTag(new ViewHolder(view));
		}
		ViewHolder holder = (ViewHolder) view.getTag();

		holder.nameView.setText(items.get(position));

		return view;
	}

	private class ViewHolder {
		final TextView nameView;

		ViewHolder(View view) {
			nameView = (TextView) view.findViewById(R.id.name_text);
		}
	}
}
