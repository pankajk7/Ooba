package com.ooba.Adapter;

import java.util.ArrayList;

import com.ooba.NewsActivity;
import com.ooba.R;
import com.ooba.entity.NewsEntity;
import com.ooba.util.FontSettings;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewListBaseAdapter extends BaseAdapter {

	Context context;
	ArrayList<NewsEntity> listDataHeader;
	LayoutInflater inflater;

	public NewListBaseAdapter(Context context,
			ArrayList<NewsEntity> listDataHeader) {
		this.context = context;
		this.listDataHeader = listDataHeader;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (this.listDataHeader == null || this.listDataHeader.size() == 0) {
			return 0;
		}
		return this.listDataHeader.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listDataHeader.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.news_items_layout, parent,
					false);
		}

		try {
			TextView newsTitleTextView = (TextView) convertView
					.findViewById(R.id.textview_new_items_layout_title);
			NewsEntity news = listDataHeader.get(arg0);
			newsTitleTextView.setText(news.Header);

			Typeface tf = new FontSettings(context).getSourceSansProRegular();
			newsTitleTextView.setTypeface(tf);

			convertView.setTag(arg0);
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>>" + e.getMessage());
		}
		return convertView;
	}

}
