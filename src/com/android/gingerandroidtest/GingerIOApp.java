package com.android.gingerandroidtest;

import android.app.Application;
import android.content.Context;

public class GingerIOApp extends Application {

	public static Context context;

	public static Context getContext() {
		return context;
	}

	@Override
	public void onCreate() {

		super.onCreate();
		context = this;
	}

}
