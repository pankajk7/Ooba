package com.ooba.Adapter;

import java.util.ArrayList;

import javax.crypto.spec.PSource;

import com.ooba.R;
import com.ooba.entity.NatureOfEnquiry;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NatureOfEnquiryBaseAdapter extends BaseAdapter{

	Context context;
	ArrayList<NatureOfEnquiry> stringList;
	LayoutInflater inflater;
	
	public NatureOfEnquiryBaseAdapter(Context context, ArrayList<NatureOfEnquiry> stringList){
		this.context = context;
		this.stringList = stringList;
		inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		if(stringList == null || stringList.size() == 0){
			return 0;
		}
		return stringList.size();
	}

	@Override
	public Object getItem(int position) {
		return stringList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.nature_of_query_items,
					parent, false);			
		}
		
		TextView text = (TextView) convertView.findViewById(R.id.textview_nature_of_query_text);
		
		text.setText(stringList.get(position).listString);
		
		return convertView;
	}

}
