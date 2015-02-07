package com.ooba.Adapter;

import java.util.ArrayList;

import com.ooba.R;
import com.ooba.entity.GridRowData;
import com.ooba.entity.NewsEntity;
import com.ooba.util.FontSettings;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RowListAdapter extends BaseAdapter {

	 ArrayList<GridRowData> rowsArrayList;
	 Context context;
	 LayoutInflater inflater;
	 public RowListAdapter(Context context,ArrayList<GridRowData> rows) {
		this.context=context;
		rowsArrayList=rows;
		inflater = (LayoutInflater) context
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	 
	@Override
	public int getCount() {
	       return rowsArrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return rowsArrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.grid_list_item, null,
					false);
		}

		try {
			
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
			
			
			GridRowData data=rowsArrayList.get(arg0);
			
			srno.setText(data.id);
			paymentamount.setText(data.paymentAmount);
			interestamount.setText(data.interestAmount);
			centtralreduction.setText(data.centralReduction);
			balancedue.setText(data.balanceDue);
			
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>>" + e.getMessage());
		}
		return convertView;
	
	}

}
