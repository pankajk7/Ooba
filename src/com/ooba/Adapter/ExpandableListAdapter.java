package com.ooba.Adapter;

import java.util.HashMap;
import java.util.List;

import com.ooba.R;
import com.ooba.util.FontSettings;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; 
	private List<String> childData;
	private HashMap<String, String> _listDataChild;

	public ExpandableListAdapter(Context context, List<String> listDataHeader,
			List<String> childData, HashMap<String, String> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		this.childData = childData;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this.childData.get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return groupPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		String childText = (String) getChild(groupPosition, childPosition);
		
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}

		WebView childDataWebView = (WebView) convertView
				.findViewById(R.id.webview_listitem_childtext);
		try {
			childDataWebView.setBackgroundColor(Color.parseColor("#fafafa"));
			childDataWebView.loadData(childText, "text/html; charset=UTF-8",null);
			
			WebSettings webSettings = childDataWebView.getSettings();

			webSettings.setDefaultFontSize(13);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}
		
		if(isExpanded){
			convertView.setBackgroundResource(R.color.faq_expandable_background);
		}else{
			convertView.setBackgroundResource(R.color.white);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		Typeface tf = new FontSettings(_context).getSourceSansProRegular();
		lblListHeader.setTypeface(tf);
		lblListHeader.setText(headerTitle);
		
		ImageView indicator = (ImageView) convertView.findViewById(R.id.imageview_list_group_indicator);
		indicator.setSelected(isExpanded);
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
