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
import com.ooba.Adapter.ExpandableListAdapter;
import com.ooba.entity.FaqEntity;
import com.ooba.util.BackgroundNetwork;
import com.ooba.util.ConnectionDetector;
import com.ooba.util.MySqliteHelper;
import com.ooba.util.OobaGoogleAnalytics;

import android.os.Bundle;
import android.os.Handler;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class FAQsActivity extends BaseClass implements
		SwipeRefreshLayout.OnRefreshListener {

	ExpandableListView expListView;
	ExpandableListAdapter listAdapter;
	ArrayList<String> listDataHeader;
	ArrayList<String> childDataList;
	HashMap<String, String> listDataChild;
	ArrayList<RssItem> rssItems;
	MySqliteHelper databaseConnections;

	private SwipeRefreshLayout mListViewContainer;
	private SwipeRefreshLayout mEmptyViewContainer;

	public int convertDpToPixel(float dp) {
		Resources resources = getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout framelayout = (FrameLayout) findViewById(R.id.layout_basecontainer);
		framelayout.addView(getLayoutInflater().inflate(R.layout.faqs_activity,
				null));

		try {
			new OobaGoogleAnalytics(FAQsActivity.this).sendTrackedEvent(
					"Faq_Page", "faq_Activity", "0");
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;

		expListView = (ExpandableListView) findViewById(R.id.expandableview_faqs_list);

		mListViewContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container_faq);
		mEmptyViewContainer = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout_emptyView_faq);

		onCreateSwipeToRefresh(mListViewContainer);
		onCreateSwipeToRefresh(mEmptyViewContainer);

		databaseConnections = new MySqliteHelper(FAQsActivity.this);
		listDataChild = new HashMap<String, String>();

		ArrayList<FaqEntity> tempFaqs = new ArrayList<FaqEntity>();
		tempFaqs = databaseConnections.getAllfaqs();
		if (tempFaqs != null) {

			listDataHeader = new ArrayList<String>();
			childDataList = new ArrayList<String>();
			FaqEntity entity;
			for (int i = 0; i < tempFaqs.size(); i++) {

				entity = (FaqEntity) tempFaqs.get(i);
				listDataHeader.add(entity.Header);
				childDataList.add(entity.Content);
				listDataChild.put(entity.Header, entity.Content);
			}
			listAdapter = new ExpandableListAdapter(FAQsActivity.this,
					listDataHeader, childDataList, listDataChild);

			expListView.setEmptyView(mEmptyViewContainer);
			expListView.setAdapter(listAdapter);

			boolean mobileDataEnabled = false;
			mobileDataEnabled = new ConnectionDetector(FAQsActivity.this)
					.isConnectingToInternet();

			if (mobileDataEnabled == false) {
				showMessage("Please ensure that you are connected to the internet");
				return;
			}

		} else {
			boolean mobileDataEnabled = false;
			mobileDataEnabled = new ConnectionDetector(FAQsActivity.this)
					.isConnectingToInternet();

			if (mobileDataEnabled == false) {
				showMessage("Please ensure that you are connected to the internet");
				return;
			}
		}

		databaseConnections.close();
		prepareListData();

		expListView.setOnGroupClickListener(new OnGroupClickListener() {
			View previousView = null;

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return false;
			}
		});

		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			int previousGroup = -1;

			@Override
			public void onGroupExpand(int groupPosition) {
				if (groupPosition != previousGroup) {
					expListView.collapseGroup(previousGroup);
					previousGroup = groupPosition;
				}

			}
		});

		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {

			}
		});

		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				return false;
			}
		});

	}

	@Override
	protected void onResume() {
		try {
			new OobaGoogleAnalytics(FAQsActivity.this).sendTrackedEvent("FAQs Page",
					"FAQs", "FAQs");
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}
		super.onResume();
	}
	private void onCreateSwipeToRefresh(SwipeRefreshLayout refreshLayout) {

		refreshLayout.setOnRefreshListener(this);

		refreshLayout.setColorScheme(android.R.color.holo_blue_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light,
				android.R.color.holo_red_light);

	}

	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				boolean mobileDataEnabled = false;
				mobileDataEnabled = new ConnectionDetector(FAQsActivity.this)
						.isConnectingToInternet();

				if (mobileDataEnabled == false) {
					showMessage("Please ensure that you are connected to the internet");
					mListViewContainer.setRefreshing(false);
					mEmptyViewContainer.setRefreshing(false);
					return;
				}

				try {
					int lastIndex = listAdapter.getGroupCount() - 1;
					if (lastIndex > -1) {
						prepareListData();
						mListViewContainer.setRefreshing(false);
					} else {
						prepareListData();
						mEmptyViewContainer.setRefreshing(false);
					}

					listAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					System.out.println(">>>Exception>>>" + e.toString()
							+ ">>>Message>>>" + e.getMessage());
				}
			}
		}, 1000);
	}

	public void showMessage(String message) {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(
				FAQsActivity.this);
		dlgAlert.setTitle("No Internet");
		dlgAlert.setMessage(message);
		dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}

	private void prepareListData() {

		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, String>();
		childDataList = new ArrayList<String>();

		new BackgroundNetwork(FAQsActivity.this) {
			boolean mobileDataEnabled = false;

			protected Void doInBackground(Void[] params) {

				mobileDataEnabled = new ConnectionDetector(FAQsActivity.this)
						.isConnectingToInternet();

				if (mobileDataEnabled == false) {
					return null;
				}

				RssHandler rssHandler = new RssHandler();

				rssHandler.getResult();

				URL url;
				try {
					url = new URL("http://ooba.2strokestaging.co.za/rss/faq");

					RssFeed feed = RssReader.read(url);

					rssItems = feed.getRssItems();
					for (RssItem rssItem : rssItems) {
						listDataHeader.add(rssItem.getTitle().trim());
						childDataList.add(rssItem.getDescription().trim());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				return null;
			};

			protected void onPostExecute(Void result) {
				try {

					if (mobileDataEnabled == false) {
						super.onPostExecute(result);
						return;
					}

					if (listDataHeader.size() <= 0)
						return;

					databaseConnections = new MySqliteHelper(FAQsActivity.this);

					if (databaseConnections != null
							&& listDataHeader.size() > 0) {
						databaseConnections.removeAllFaq();
					}

					for (int i = 0; i < listDataHeader.size(); i++) {
						String header;
						String child;
						if (listDataHeader.get(i) != null) {
							header = listDataHeader.get(i);
						} else {
							header = "";
						}

						if (childDataList != null) {
							child = childDataList.get(i);
						} else {
							child = "";
						}

						if (databaseConnections != null) {
							databaseConnections.insertFaq(header, child);
						}
						listDataChild.put(header, child);
					}

					listAdapter = new ExpandableListAdapter(FAQsActivity.this,
							listDataHeader, childDataList, listDataChild);

					expListView.setEmptyView(mEmptyViewContainer);
					expListView.setAdapter(listAdapter);
				} catch (Exception e) {
					System.out.println(">>>Exception>>>" + e.toString()
							+ ">>>Message>>>" + e.getMessage());
				}
				super.onPostExecute(result);
			};

		}.execute();

	}

	@Override
	protected void onDestroy() {
		databaseConnections.close();
		super.onDestroy();
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

}
