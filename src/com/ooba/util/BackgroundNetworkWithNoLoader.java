package com.ooba.util;

import com.ooba.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.Window;

public class BackgroundNetworkWithNoLoader extends AsyncTask<Void, Void, Void> {

	private ProgressDialog dialog;
	Context context;

	public BackgroundNetworkWithNoLoader(Context activity) {
		context = activity;
		dialog = new ProgressDialog(context);
	}

	protected void doActionOnPostExecuteBeforeProgressDismiss() {

	}

	protected void doActionOnPostExecuteAfterProgressDismiss() {

	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		doActionOnPostExecuteBeforeProgressDismiss();
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		doActionOnPostExecuteAfterProgressDismiss();
	}

	public void onPostExecuteDeveloperMethodForPublicAccess(Void result) {
		super.onPostExecute(result);
		doActionOnPostExecuteBeforeProgressDismiss();
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		doActionOnPostExecuteAfterProgressDismiss();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		this.dialog.setMessage("Please Wait");
		this.dialog.setCancelable(false);
	}

	@Override
	protected Void doInBackground(Void... params) {
		return null;
	}
	
	

}
