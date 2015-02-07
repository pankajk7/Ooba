package com.ooba.Adapter;

import java.util.ArrayList;

import com.ooba.R;
import com.ooba.entity.AmmortisationHeader;
import com.ooba.entity.GridRowData;
import com.ooba.util.FontSettings;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class GridListAdapter extends BaseExpandableListAdapter {

	ArrayList<AmmortisationHeader> header;
	ArrayList<ArrayList<GridRowData>> listchilds;
	Context context;
	RowListAdapter rowsAdapter;

	public GridListAdapter(Context context, ArrayList<AmmortisationHeader> head,
			ArrayList<ArrayList<GridRowData>> childs) {
		header = head;
		listchilds = childs;
		this.context = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return header.get(groupPosition).gridArrayList.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return groupPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.grid_list_item,
					null);
		}

		ListView listview = (ListView) convertView
				.findViewById(R.id.list_child_rows);
		if((childPosition % 2) == 0){
			convertView.setBackgroundResource(R.color.white);			
		}else{
			convertView.setBackgroundResource(R.color.ammortisation_row_background);
		}
		
		TextView srno= (TextView) convertView
				.findViewById(R.id.textview_rowlistitem_srno);
				
				TextView paymentamount= (TextView) convertView
				.findViewById(R.id.textview_rowlistitem_paymentamount);
				
				TextView interestamount= (TextView) convertView
				.findViewById(R.id.textview_rowlistitem_interestamount);
				
				TextView centtralreduction= (TextView) convertView
				.findViewById(R.id.textview_rowlistitem_centralreduction);
				
				TextView balancedue= (TextView) convertView
				.findViewById(R.id.textview_rowlistitem_balancedue);
				
				
				GridRowData data=header.get(groupPosition).gridArrayList.get(childPosition);;
				
				srno.setText(data.id);
				paymentamount.setText(data.paymentAmount);
				interestamount.setText(data.interestAmount);
				centtralreduction.setText(data.centralReduction);
				balancedue.setText(data.balanceDue);
				
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return header.get(groupPosition).gridArrayList.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return header.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return header.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context

			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}

		String headerTitle = header.get(groupPosition).Heading;
		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		Typeface tf = new FontSettings(context).getSourceSansProRegular();
		lblListHeader.setTypeface(tf);
		lblListHeader.setText(headerTitle);

		ImageView indicator = (ImageView) convertView
				.findViewById(R.id.imageview_list_group_indicator);
		indicator.setSelected(isExpanded);

		return convertView;

	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
