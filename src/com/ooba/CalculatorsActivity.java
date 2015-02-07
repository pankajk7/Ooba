package com.ooba;

import com.google.analytics.tracking.android.EasyTracker;
import com.ooba.services.OobaWebServices;
import com.ooba.util.BackgroundNetwork;
import com.ooba.util.BackgroundNetworkWithNoLoader;
import com.ooba.util.FontSettings;
import com.ooba.util.OobaGoogleAnalytics;

import android.os.Bundle;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CalculatorsActivity extends BaseClass {

	String backPressStateString = "";
	String currentLayout = "";
	LinearLayout affordabilityLayout;
	LinearLayout bondRepaymentCalculatorLayout;
	LinearLayout homeLoanCalculatorLayout;
	LinearLayout bondTransferCalculatorLayout;
	LinearLayout homeLoanAmmortisionLayout;

	public static double interestRate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			new OobaGoogleAnalytics(CalculatorsActivity.this).sendTrackedEvent(
					"Calculators_Page", "Calculators_Activity", "0");
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		new BackgroundNetworkWithNoLoader(CalculatorsActivity.this) {
			protected Void doInBackground(Void[] params) {

				interestRate = new OobaWebServices().getCurrentInterestRate();

				if (Double.compare(interestRate, 0.0) < 0
						|| Double.compare(interestRate, 0.0) == 0) {
					interestRate = 9.0;
				}
				return null;
			};

			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
			}
		}.execute();

		createCalculatorLayout();
	}

	public void createCalculatorLayout() {

		TextView affordabilityTitleTextView;
		TextView affordabilityTextTextView;
		TextView bondRepaymentTitleTextView;
		TextView bondRepaymentTextTextView;
		TextView bondTransferTitleTextView;
		TextView bondTransferTextTextView;
		TextView homeLoanTitleTextView;
		TextView homeLoanTextTextView;
		TextView homeDepositTitleTextView;
		TextView homeDepositTextTextView;

		FrameLayout framelayout = (FrameLayout) findViewById(R.id.layout_basecontainer);
		framelayout.removeAllViews();
		framelayout.addView(getLayoutInflater().inflate(
				R.layout.calculators_activity, null));

		affordabilityLayout = (LinearLayout) findViewById(R.id.layout_calculator_layout_affordability);
		bondRepaymentCalculatorLayout = (LinearLayout) findViewById(R.id.layout_calculator_layout_bondreplaymentcalculator);
		homeLoanCalculatorLayout = (LinearLayout) findViewById(R.id.homeloan_depositsavingLayout);
		bondTransferCalculatorLayout = (LinearLayout) findViewById(R.id.layoutBondTransferCost);
		homeLoanAmmortisionLayout = (LinearLayout) findViewById(R.id.layout_homeloan_ammorision);

		affordabilityTitleTextView = (TextView) findViewById(R.id.textview_calculator_affordability_title);
		affordabilityTextTextView = (TextView) findViewById(R.id.textview_calculator_affordability_text);
		bondRepaymentTitleTextView = (TextView) findViewById(R.id.textview_calculator_bondrepayment_title);
		bondRepaymentTextTextView = (TextView) findViewById(R.id.textview_calculator_bondrepayment_text);
		bondTransferTitleTextView = (TextView) findViewById(R.id.textview_calculator_bondtransfer_title);
		bondTransferTextTextView = (TextView) findViewById(R.id.textview_calculator_bondtransfer_text);
		homeLoanTitleTextView = (TextView) findViewById(R.id.textview_calculator_homeloan_title);
		homeLoanTextTextView = (TextView) findViewById(R.id.textview_calculator_homeloan_text);
		homeDepositTitleTextView = (TextView) findViewById(R.id.textview_calculator_homeloandeposit_title);
		homeDepositTextTextView = (TextView) findViewById(R.id.textview_calculator_homeloandeposit_text);

		Typeface tf = new FontSettings(CalculatorsActivity.this)
				.getSourceSansProRegular();
		affordabilityTitleTextView.setTypeface(tf);
		affordabilityTextTextView.setTypeface(tf);
		bondRepaymentTitleTextView.setTypeface(tf);
		bondRepaymentTextTextView.setTypeface(tf);
		bondTransferTitleTextView.setTypeface(tf);
		bondTransferTextTextView.setTypeface(tf);
		homeLoanTitleTextView.setTypeface(tf);
		homeLoanTextTextView.setTypeface(tf);
		homeDepositTitleTextView.setTypeface(tf);
		homeDepositTextTextView.setTypeface(tf);

		affordabilityLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				backPressStateString = "calculatorHome";

				backButton.setVisibility(View.VISIBLE);
				image.setVisibility(View.VISIBLE);
				backButtonLinearLayout
						.setBackgroundResource(R.color.base_top_bar_Background1);
				infoLayout
						.setBackgroundResource(R.color.base_top_bar_Background1);
				new AffordabilityCalculatorActivity(CalculatorsActivity.this)
						.createLayout();
			}
		});

		bondRepaymentCalculatorLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				backPressStateString = "calculatorHome";

				backButton.setVisibility(View.VISIBLE);
				backButtonLinearLayout
						.setBackgroundResource(R.color.base_top_bar_Background1);
				image.setVisibility(View.VISIBLE);
				infoLayout
						.setBackgroundResource(R.color.base_top_bar_Background1);
				BondRepaymentCalculator bondCalc = new BondRepaymentCalculator(
						CalculatorsActivity.this);
				bondCalc.createLayout();

			}
		});
		homeLoanCalculatorLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				backPressStateString = "calculatorHome";

				backButton.setVisibility(View.VISIBLE);
				backButtonLinearLayout
						.setBackgroundResource(R.color.base_top_bar_Background1);
				image.setVisibility(View.VISIBLE);
				infoLayout
						.setBackgroundResource(R.color.base_top_bar_Background1);
				HomeLoanDepositSavingCalc bondCalc = new HomeLoanDepositSavingCalc(
						CalculatorsActivity.this);
				bondCalc.createLayout();

			}
		});

		bondTransferCalculatorLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				backPressStateString = "calculatorHome";

				backButton.setVisibility(View.VISIBLE);
				backButtonLinearLayout
						.setBackgroundResource(R.color.base_top_bar_Background1);
				image.setVisibility(View.VISIBLE);
				infoLayout
						.setBackgroundResource(R.color.base_top_bar_Background1);
				BondAndTransferCostCalculator bond = new BondAndTransferCostCalculator(
						CalculatorsActivity.this);
				bond.createLayout();

			}
		});
		homeLoanAmmortisionLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				backPressStateString = "calculatorHome";
				backButton.setVisibility(View.VISIBLE);
				backButtonLinearLayout
						.setBackgroundResource(R.color.base_top_bar_Background1);
				image.setVisibility(View.VISIBLE);
				infoLayout
						.setBackgroundResource(R.color.base_top_bar_Background1);
				HomeLoanAmortisationCalculator home = new HomeLoanAmortisationCalculator(
						CalculatorsActivity.this);
				home.createLayout();

			}
		});
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

				View d = getCurrentFocus();
				if (d == null)
					return;
				inputManager.hideSoftInputFromWindow(d.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);

				backPressStateString = "";
				backButton.setVisibility(View.INVISIBLE);
				image.setVisibility(View.INVISIBLE);
				infoLayout
						.setBackgroundResource(R.color.base_top_bar_Background);
				backButtonLinearLayout
						.setBackgroundResource(R.color.base_top_bar_Background);
				createCalculatorLayout();
			}
		});

	}

	@Override
	protected void onResume() {

		try {
			new OobaGoogleAnalytics(CalculatorsActivity.this).sendTrackedEvent("Caluclators Page",
					"Calculators", "Calculators");
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}

		super.onResume();
	}

	@Override
	public void onBackPressed() {
		if (backPressStateString.equals("calculatorHome")) {
			backPressStateString = "";
			backButton.setVisibility(View.INVISIBLE);
			image.setVisibility(View.INVISIBLE);
			infoLayout.setBackgroundResource(R.color.base_top_bar_Background);
			backButtonLinearLayout
					.setBackgroundResource(R.color.base_top_bar_Background);
			createCalculatorLayout();
		} else {
			finish();
		}
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
