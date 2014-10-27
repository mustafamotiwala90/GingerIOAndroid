package com.android.gingerandroidtest;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements
LoaderCallbacks<List<EventData>> {

	public static String upcomingEvents = "upcomingEvents";
	public static String historyEvents = "historyEvents";

	public static String eventsUrl = "https://graph.facebook.com/OffTheGridSF/events?access_token=";
	private LoaderManager.LoaderCallbacks<List<EventData>> mCallbacks;
	LinearLayout loadingProgress;
	ActionBar actionBar;
	Fragment upEventsFragment;
	Fragment historyFragment;
	ActionBar.Tab upcomingEventsTab;
	ActionBar.Tab historyEventsTab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		loadingProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
		loadingProgress.setVisibility(View.VISIBLE);

		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		upcomingEventsTab = actionBar.newTab().setText(upcomingEvents);
		historyEventsTab = actionBar.newTab().setText(historyEvents);
		eventsUrl = eventsUrl + getString(R.string.access_token);
		mCallbacks = this;
		LoaderManager lm = getLoaderManager();
		Loader<List<EventData>> loader = lm.initLoader(
				LoaderId.EventListLoaderid, null, mCallbacks);
		loader.forceLoad();
	}

	public void updateView(List<EventData> result) {
		upEventsFragment = upcomingEventsFragment.newInstance(result);
		historyFragment = HistoryEventsFragment.newInstance();

		upcomingEventsTab.setTabListener(new TabListener(upEventsFragment));
		historyEventsTab.setTabListener(new TabListener(historyFragment));

		actionBar.addTab(upcomingEventsTab);
		actionBar.addTab(historyEventsTab);

	}

	@Override
	public Loader<List<EventData>> onCreateLoader(int id, Bundle args) {
		return new EventListLoader(eventsUrl);
	}

	@Override
	public void onLoadFinished(Loader<List<EventData>> loader,
			List<EventData> result) {
		loadingProgress.setVisibility(View.GONE);
		MyHandler myhandler = new MyHandler(result);
		myhandler.sendEmptyMessage(0);
	}

	@Override
	public void onLoaderReset(Loader<List<EventData>> loader) {
		// TODO Auto-generated method stub
	}

	public class MyHandler extends Handler {
		List<EventData> result;

		MyHandler(List<EventData> result) {
			this.result = result;
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			updateView(result);
		}
	}
}
