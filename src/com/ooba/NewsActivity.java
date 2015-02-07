package com.ooba;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.widget.SwipeRefreshLayout;
import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssHandler;
import nl.matshofman.saxrssreader.RssItem;
import nl.matshofman.saxrssreader.RssReader;

import com.google.analytics.tracking.android.EasyTracker;
import com.ooba.Adapter.NewListBaseAdapter;
import com.ooba.entity.NewsEntity;
import com.ooba.util.BackgroundNetwork;
import com.ooba.util.ConnectionDetector;
import com.ooba.util.MySqliteHelper;
import com.ooba.util.OobaGoogleAnalytics;

import android.os.Bundle;
import android.os.Handler;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;

public class NewsActivity extends BaseClass implements
		SwipeRefreshLayout.OnRefreshListener {

	ListView newsListView;
	NewListBaseAdapter listAdapter;
	ArrayList<String> listDataHeader;
	HashMap<String, String> listDataChild;
	ArrayList<String> childDataList;
	ArrayList<RssItem> rssItems;
	String backPressStateString = "";
	MySqliteHelper databaseConnection;
	ArrayList<NewsEntity> newsEntitiesArrayList;

	private SwipeRefreshLayout mListViewContainer;
	private SwipeRefreshLayout mEmptyViewContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			new OobaGoogleAnalytics(NewsActivity.this).sendTrackedEvent(
					"News_Page",
					"News_Activity", "0");
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}
		
		createNewsLayout();
	}
	
	@Override
	protected void onResume() {
		try {
			new OobaGoogleAnalytics(NewsActivity.this).sendTrackedEvent("News List Page",
					"News List", "News List");
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		if (backPressStateString.equals("NewsTitlePage")) {
			backPressStateString = "";
			backButton.setVisibility(View.INVISIBLE);
			backButtonLinearLayout
					.setBackgroundResource(R.color.base_top_bar_Background);
			createNewsLayout();
		} else {
			finish();
		}
	}

	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				boolean mobileDataEnabled = false;
				mobileDataEnabled = new ConnectionDetector(NewsActivity.this)
						.isConnectingToInternet();

				if (mobileDataEnabled == false) {
					showMessage("Please ensure that you are connected to the internet");
					mListViewContainer.setRefreshing(false);
					mEmptyViewContainer.setRefreshing(false);
					return;
				}
				int lastIndex = listAdapter.getCount() - 1;
				if (lastIndex > -1) {
					prepareListData();
					mListViewContainer.setRefreshing(false);
				} else {
					prepareListData();
					mEmptyViewContainer.setRefreshing(false);
				}

				listAdapter.notifyDataSetChanged();
			}
		}, 1000);
	}

	public void showMessage(String message) {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(
				NewsActivity.this);
		dlgAlert.setTitle("No Internet");
		dlgAlert.setMessage(message);
		dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}

	public void createNewsLayout() {
		FrameLayout framelayout = (FrameLayout) findViewById(R.id.layout_basecontainer);
		framelayout.removeAllViews();
		framelayout.addView(getLayoutInflater().inflate(R.layout.news_activity,
				null));

		newsListView = (ListView) findViewById(R.id.listview_news_activity_layout_list);

		mListViewContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		mEmptyViewContainer = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout_emptyView);

		onCreateSwipeToRefresh(mListViewContainer);
		onCreateSwipeToRefresh(mEmptyViewContainer);

		if (newsEntitiesArrayList == null || newsEntitiesArrayList.size() == 0) {
			databaseConnection = new MySqliteHelper(NewsActivity.this);
			newsEntitiesArrayList = new ArrayList<NewsEntity>();
			newsEntitiesArrayList = databaseConnection.getAllNews();
			listAdapter = new NewListBaseAdapter(NewsActivity.this,
					newsEntitiesArrayList);
			newsListView.setEmptyView(mEmptyViewContainer);
			newsListView.setAdapter(listAdapter);
			prepareListData();

		} else {
			listAdapter = new NewListBaseAdapter(NewsActivity.this,
					newsEntitiesArrayList);
			newsListView.setAdapter(listAdapter);
			newsListView.setEmptyView(mEmptyViewContainer);
		}

		newsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				backButton.setVisibility(View.VISIBLE);
				backButtonLinearLayout
						.setBackgroundResource(R.color.base_top_bar_Background1);
				String news = newsEntitiesArrayList.get(position).Content;
				String header = newsEntitiesArrayList.get(position).Header;
				if (news == null) {
					news = "";
				}
				backPressStateString = "NewsTitlePage";
				new NewsDetailPageActivity(NewsActivity.this)
						.createNewsDetailPageLayout(news, header);
			}
						
		});
		
	
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				backPressStateString = "";
				backButton.setVisibility(View.GONE);
				backButtonLinearLayout
						.setBackgroundResource(R.color.base_top_bar_Background);
				createNewsLayout();
			}
		});

	}

	private void onCreateSwipeToRefresh(SwipeRefreshLayout refreshLayout) {

		refreshLayout.setOnRefreshListener(this);
		refreshLayout.setColorScheme(android.R.color.holo_blue_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light,
				android.R.color.holo_red_light);

	}

	private void prepareListData() {

		listDataHeader = new ArrayList<String>();
		childDataList = new ArrayList<String>();

		new BackgroundNetwork(NewsActivity.this) {
			boolean mobileDataEnabled = false; 
			protected Void doInBackground(Void[] params) {
				
				
				mobileDataEnabled = new ConnectionDetector(NewsActivity.this)
						.isConnectingToInternet();

				if (mobileDataEnabled == false) {			
					return null;
				}

				RssHandler rssHandler = new RssHandler();

				rssHandler.getResult();

				URL url;
				try {
					url = new URL("http://ooba.2strokestaging.co.za/rss/news");

					RssFeed feed = RssReader.read(url);
					databaseConnection = new MySqliteHelper(NewsActivity.this);
					databaseConnection.removeAllnews();

					newsEntitiesArrayList.clear();
					rssItems = feed.getRssItems();
					for (RssItem rssItem : rssItems) {
						NewsEntity news = new NewsEntity(0, rssItem.getTitle(),
								rssItem.getDescription());
						newsEntitiesArrayList.add(news);
						databaseConnection.insertNews(rssItem.getTitle(),
								rssItem.getDescription());
					}
				} catch (Exception e) {
					System.out.println(">>>Exception>>>" + e.toString()
							+ ">>>Message>>>" + e.getMessage());
				}

				return null;
			};

			protected void onPostExecute(Void result) {

				if(mobileDataEnabled == false){							
					super.onPostExecute(result);
					showMessage("Please ensure that you are connected to the internet");	
					return;
				}
				
				try {
					listAdapter = new NewListBaseAdapter(NewsActivity.this,
							newsEntitiesArrayList);
					newsListView.setEmptyView(mEmptyViewContainer);
					newsListView.setAdapter(listAdapter);
				} catch (Exception e) {
					System.out.println(">>>Exception>>>" + e.toString()
							+ ">>>Message>>>" + e.getMessage());
				}
				super.onPostExecute(result);
			};
		}.execute();
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}
 
	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}
//	public static void setListViewHeightBasedOnChildren(ListView listView) {
//		ListAdapter listAdapter = listView.getAdapter();
//		if (listAdapter == null)
//			return;
//
//		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
//				MeasureSpec.UNSPECIFIED);
//		int totalHeight = 0;
//		View view = null;
//		for (int i = 0; i < listAdapter.getCount(); i++) {
//			view = listAdapter.getView(i, view, listView);
//			if (i == 0)
//				view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
//						LayoutParams.WRAP_CONTENT));
//
//			view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
//			totalHeight += view.getMeasuredHeight();
//		}
//		ViewGroup.LayoutParams params = listView.getLayoutParams();
//		params.height = totalHeight
//				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//		listView.setLayoutParams(params);
//		listView.requestLayout();
//	}
}
