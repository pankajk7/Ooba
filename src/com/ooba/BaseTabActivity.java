package com.ooba;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class BaseTabActivity extends TabActivity {

	ImageView image;
	public static TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.base_tab_activity);
			
			setupTabHost();
            tabHost.getTabWidget().setClipChildren(false);            
            
			setTabs();
			
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>>" + e.getMessage());
		}
	}

	public void switchTab(int tab) {
		tabHost.setCurrentTab(tab);
	}

	private void setupTabHost() {
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this.getLocalActivityManager());
	}

	public void setTabs() {
		try {
			addTab("Calculators", R.drawable.calc_tab_icon_selector,
					CalculatorsActivity.class);
			addTab("FAQs", R.drawable.faq_selector, FAQsActivity.class);
			addTab("News", R.drawable.news_selector, NewsActivity.class);
			addTab("Contact", R.drawable.contact_selector,
					ContactUsActivity.class);
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>>" + e.getMessage());
		}
	}

	public void addTab(String labelId, int drawableId, Class<?> c) {
		TabHost tabHost = getTabHost();
		Intent intent = new Intent().setClass(this, c);
		TabSpec spec = tabHost.newTabSpec(labelId);

		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator
				.findViewById(R.id.textview_tabtext);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator
				.findViewById(R.id.imageview_tabicon);
		icon.setImageResource(drawableId);

		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}

}
