package com.ooba.util;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.ooba.R;
import com.ooba.Adapter.NatureOfEnquiryBaseAdapter;
import com.ooba.entity.NatureOfEnquiry;

public class DialogList {
	Context context;

	public DialogList(Context context) {
		this.context = context;

	}

	public void showList(final ArrayList<NatureOfEnquiry> stringList,
			final EditText yearToRepayTextView, final View nextControl) {
		final Dialog dialog1 = new Dialog(context);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog1.setContentView(R.layout.nature_of_query_layout);
		dialog1.setCancelable(false);
		ListView stringListView = (ListView) dialog1
				.findViewById(R.id.listview_nature_of_enquiry_listiview);

		NatureOfEnquiryBaseAdapter adapter = new NatureOfEnquiryBaseAdapter(
				context, stringList);
		stringListView.setAdapter(adapter);

		stringListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				yearToRepayTextView.setText(stringList.get(position).listString);
				dialog1.dismiss();
			}

		});

		dialog1.show();

		dialog1.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				nextControl.post(new Runnable() {
					public void run() {
						nextControl.requestFocusFromTouch();
						nextControl.performClick();
//						InputMethodManager lManager = (InputMethodManager) context
//								.getSystemService(Context.INPUT_METHOD_SERVICE);
//						lManager.showSoftInput(nextControl, 0);
					}
				});

			}
		});
	}

	public void showList(final ArrayList<NatureOfEnquiry> stringList,
			final TextView yearToRepayTextView) {
		final Dialog dialog1 = new Dialog(context);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog1.setContentView(R.layout.nature_of_query_layout);
		dialog1.setCancelable(false);
		ListView stringListView = (ListView) dialog1
				.findViewById(R.id.listview_nature_of_enquiry_listiview);

		NatureOfEnquiryBaseAdapter adapter = new NatureOfEnquiryBaseAdapter(
				context, stringList);
		stringListView.setAdapter(adapter);

		stringListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				yearToRepayTextView.setText(stringList.get(position).listString);
				dialog1.dismiss();
			}

		});

		dialog1.show();

		dialog1.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {

			}
		});
	}

	public void showList(final ArrayList<NatureOfEnquiry> stringList,
			final EditText yearToRepayTextView) {
		final Dialog dialog1 = new Dialog(context);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog1.setContentView(R.layout.nature_of_query_layout);

		ListView stringListView = (ListView) dialog1
				.findViewById(R.id.listview_nature_of_enquiry_listiview);

		NatureOfEnquiryBaseAdapter adapter = new NatureOfEnquiryBaseAdapter(
				context, stringList);
		stringListView.setAdapter(adapter);

		stringListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				yearToRepayTextView.setText(stringList.get(position).listString);
				dialog1.dismiss();
			}

		});

		dialog1.show();

		dialog1.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				
			}
		});
	}
}
