package com.ooba.util;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.StandardExceptionParser;

import android.content.Context;

public class OobaGoogleAnalytics {

	Context context;
	private EasyTracker easyTracker = null;
	
	public OobaGoogleAnalytics(Context context){
		this.context = context;
		easyTracker = EasyTracker.getInstance(context);
	}
	
	public void sendTrackedEvent(String actionString, String eventNameString, String idString){
		easyTracker.send(MapBuilder.createEvent(actionString,
				eventNameString, idString, null).build());
	}	
	
	public void sendExceptionEvent(String threadNameString, Exception e){
		easyTracker.send(MapBuilder.createException(
				new StandardExceptionParser(context, null)
						.getDescription(threadNameString, e), false).build());
	}
	
}
