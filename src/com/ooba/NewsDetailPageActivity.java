package com.ooba;

import com.ooba.util.OobaGoogleAnalytics;

import android.content.Context;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsDetailPageActivity {

	Context context;
	WebView newsDetailWebView;
	TextView titleHeadingTextView;

	public NewsDetailPageActivity(Context context) {
		this.context = context;
	}

	public void createNewsDetailPageLayout(String childData,String header){
		
		try {
			new OobaGoogleAnalytics(context).sendTrackedEvent("News Detail Page",
					"News Detail", "News Detail");
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}
		
		FrameLayout baseLayout = (FrameLayout) ((NewsActivity) context)
				.findViewById(R.id.layout_basecontainer);

		LinearLayout inflateLayout = (LinearLayout) ((NewsActivity) context)
				.getLayoutInflater().inflate(R.layout.news_detailpage_layout,
						null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);

		newsDetailWebView = (WebView) ((NewsActivity) context)
				.findViewById(R.id.webview_news_detailpage_detailtext);
		titleHeadingTextView = (TextView)((NewsActivity) context).findViewById(R.id.textview_news_detailpage_heading);
		
		titleHeadingTextView.setText(header);
		newsDetailWebView.getSettings().setJavaScriptEnabled(true);
		newsDetailWebView.loadDataWithBaseURL("", childData, "text/html",
				"UTF-8", "");
	}
}
