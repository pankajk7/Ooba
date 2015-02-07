package com.ooba;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ooba.util.DoubleFormaterClass;
import com.ooba.util.OobaFormattingInputFields;
import com.ooba.util.OobaGoogleAnalytics;
import com.ooba.util.OobaValidation;

import za.co.ooba.calculator.BondCalculationService;
import za.co.ooba.calculator.OobaCalculator;
import za.co.ooba.calculator.deposit.saving.*;
import za.co.ooba.calculator.deposit.*;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView.OnEditorActionListener;

public class HomeLoanDepositSavingCalc {

	Context context;
	EditText propertyvalueEditText;
	EditText depositRequiredEditText;
	EditText currentSavingAmountEditText;
	EditText byWhenDepositEditText;
	EditText savingInterestRateEditText;
	LinearLayout layoutAffordability;
	Button calculate;
	Button preQualify;
	TextView prequalifyTextView;

	TextView totalSavingAmountTextView;
	TextView monthlySavingAmountTextView;
	TextView depositAmountTextView;
	boolean isClicked = false;
	
	int mYear;
	int mMonth;
	int mDay;

	public HomeLoanDepositSavingCalc(Context context) {

		this.context = context;

	}

	public void createLayout() {
		try {
			new OobaGoogleAnalytics(context).sendTrackedEvent("Home Loan Deposit Saving Calculator Page", "Home Loan Deposit Saving Calculator", "Home Loan Deposit Saving Calculator");
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}
		
		FrameLayout framelayout = (FrameLayout) ((CalculatorsActivity) context)
				.findViewById(R.id.layout_basecontainer);
		framelayout.removeAllViews();
		framelayout.addView(((CalculatorsActivity) context).getLayoutInflater()
				.inflate(R.layout.home_loan_deposit_saving_calc, null));

		propertyvalueEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_homeloan_deposit_savingcalc_propertyvalue);
		depositRequiredEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_homeloan_deposit_savingcalc_depositrequired);
		currentSavingAmountEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_homeloan_deposit_savingcalc_current_saving_amount);
		byWhenDepositEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_homeloan_deposit_savingcalc_bywhen);
		savingInterestRateEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_homeloan_deposit_savingcalc_interest_rate);
		layoutAffordability = (LinearLayout) framelayout
				.findViewById(R.id.layout_affordability);
		calculate = (Button) ((CalculatorsActivity) context)
				.findViewById(R.id.button_homeloanDepositsaving_calculate);
		preQualify = (Button) ((CalculatorsActivity) context)
				.findViewById(R.id.button_homeloanDepositsaving_prequalify);
		prequalifyTextView = (TextView) framelayout
				.findViewById(R.id.textview_affordability_calculator_prequalify);
		totalSavingAmountTextView = (TextView) framelayout
				.findViewById(R.id.textview_home_loan_deposit_totalsavingamount);
		monthlySavingAmountTextView = (TextView) framelayout
				.findViewById(R.id.textview_home_loan_deposit_monthlysavingamount);
		depositAmountTextView = (TextView) framelayout
				.findViewById(R.id.textview_home_loan_deposit_depositamount);

		preQualify.setVisibility(View.INVISIBLE);
		layoutAffordability.setVisibility(View.GONE);

		depositRequiredEditText.setText("10.0" + "%");
		
		savingInterestRateEditText.setText("%");

		new OobaFormattingInputFields(context, calculate)
				.oobaInterestRateFormatterShowKeyboard(savingInterestRateEditText);
		new OobaFormattingInputFields(context)
				.oobaInterestRateFormatterNotLast(depositRequiredEditText);
		new OobaFormattingInputFields(context)
				.oobaThousandFormatter(propertyvalueEditText);
		new OobaFormattingInputFields(context)
				.oobaThousandFormatter(currentSavingAmountEditText);

		calculate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					new OobaGoogleAnalytics(context).sendTrackedEvent("Button Click", "Home Loan Deposit Saving Calculator", calculate.getText().toString());
				} catch (Exception e) {
					System.out.println(">>>Exception>>>" + e.toString()
							+ ">>>Message>>" + e.getMessage());
				}

				if (new OobaValidation(context)
						.homeLoanDepositSaving(propertyvalueEditText,
								depositRequiredEditText,
								currentSavingAmountEditText,
								savingInterestRateEditText) == true) {
					OobaCalculator oobaCalculator = new OobaCalculator();
					DepositSavingCalculationDTO depositSavingCalculationDTO = new DepositSavingCalculationDTO();

					double propertyvalueDouble = convertToDouble(new OobaFormattingInputFields(
							context)
							.oobaGetThousandFormatter(propertyvalueEditText));
					double bondTransferCost = 0.0;
					double currentSavingAmountDouble = convertToDouble(new OobaFormattingInputFields(
							context)
							.oobaGetThousandFormatter(currentSavingAmountEditText));
					Date byWhenDepositDate = dateFormatYYMMDDCalendar(byWhenDepositEditText
							.getText().toString().trim());
					String temp1 = depositRequiredEditText.getText().toString()
							.trim();
					temp1 = temp1.replace("%", "");
					double depositRequiredDouble = convertToDouble(temp1);
					String temp = savingInterestRateEditText.getText()
							.toString().trim();
					temp = temp.replace("%", "");
					double savingInterestRateDouble = convertToDouble(temp);
					double arg6 = 0.0;

					String dateDepositRequired = "";
					try {
						SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
						dateDepositRequired = dt.format(byWhenDepositDate);
					} catch (Exception e) {
						System.out.println(">>>Exception>>>" + e.toString()
								+ ">>>Message>>>" + e.getMessage());
					}
					
					try {
						
						
						depositSavingCalculationDTO = oobaCalculator
								.calculateDepositSaving(propertyvalueDouble,
										depositRequiredDouble,
										bondTransferCost,
										currentSavingAmountDouble,
										dateDepositRequired,
										savingInterestRateDouble,
										arg6);
								
						if (depositSavingCalculationDTO.isError()) {
							showMessage(depositSavingCalculationDTO.getErrors());
						} else {
							totalSavingAmountTextView.setText(" R "
									+ DoubleFormaterClass
											.priceWithDecimal(depositSavingCalculationDTO
													.getTotalSavingsRequiredAmount()));
							monthlySavingAmountTextView.setText(" R "
									+ DoubleFormaterClass
											.priceWithDecimal(depositSavingCalculationDTO
													.getMonthlySavingAmount()));

							depositAmountTextView.setText(" R "
									+ DoubleFormaterClass
											.priceWithDecimal(depositSavingCalculationDTO
													.getDepositAmount()));

							calculate.setText("RECALCULATE");
							preQualify.setVisibility(View.VISIBLE);
							layoutAffordability.setVisibility(View.VISIBLE);
							prequalifyTextView.setFocusableInTouchMode(true);
							prequalifyTextView.requestFocus();

						}
					} catch (Exception e) {
						System.out.println(">>>Exception>>>" + e.toString()
								+ ">>>Message>>>" + e.getMessage());
					}
				}

			}
		});

		preQualify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showContactUs();
			}
		});

		prequalifyTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showContactUs();
			}
		});

		Calendar cal = Calendar.getInstance();
		mYear = cal.get(Calendar.YEAR);
		mMonth = cal.get(Calendar.MONTH);
		mDay = cal.get(Calendar.DAY_OF_MONTH);

		currentSavingAmountEditText
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_NEXT
								|| actionId == EditorInfo.IME_ACTION_DONE) {
							byWhenDepositEditText.performClick();
						}
						return false;
					}
				});
		byWhenDepositEditText.setText(mYear + "-" + getString((mMonth + 1))
				+ "-" + getString(mDay));
		byWhenDepositEditText.setFocusable(false);
		byWhenDepositEditText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DatePickerDialog dpd = new DatePickerDialog(context,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								byWhenDepositEditText.setText(year + "-"
										+ getString((monthOfYear + 1)) + "-"
										+ getString(dayOfMonth));
								
								mYear = year;
								mMonth = monthOfYear;
								mDay = dayOfMonth;

								savingInterestRateEditText
										.setFocusableInTouchMode(false);
								savingInterestRateEditText.setFocusable(false);
								savingInterestRateEditText
										.setFocusableInTouchMode(true);
								savingInterestRateEditText.setFocusable(true);
								savingInterestRateEditText.requestFocus();
								
								(new Handler()).postDelayed(new Runnable() {

									public void run() {
										InputMethodManager imm = (InputMethodManager) context
												.getSystemService(Context.INPUT_METHOD_SERVICE);
										imm.showSoftInput(savingInterestRateEditText,
												InputMethodManager.SHOW_IMPLICIT);
									}
								}, 200);
							}

						}, mYear, mMonth, mDay);
				dpd.show();
			}
		});

	}
	
	public void showContactUs() {
		try {
			new OobaGoogleAnalytics(context).sendTrackedEvent("Button Click", "Home Loan Deposit Saving Calculator", "Prequalify Now");
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}
		
		BaseTabActivity.tabHost.getTabWidget().removeView(
				BaseTabActivity.tabHost.getTabWidget().getChildTabViewAt(3));
		TabSpec spec = BaseTabActivity.tabHost.newTabSpec("contact");
		View tabIndicator = LayoutInflater.from(context).inflate(
				R.layout.tab_indicator, BaseTabActivity.tabHost.getTabWidget(),
				false);
		TextView title = (TextView) tabIndicator
				.findViewById(R.id.textview_tabtext);
		title.setText("Contact");
		ImageView icon = (ImageView) tabIndicator
				.findViewById(R.id.imageview_tabicon);
		icon.setImageResource(R.drawable.contact_selector);

		spec.setIndicator(tabIndicator);
		Intent intent = new Intent(context, ContactUsActivity.class);
		spec.setContent(intent);
		BaseTabActivity.tabHost.addTab(spec);
		BaseTabActivity.tabHost.setCurrentTab(3);

		Intent dataIntent = new Intent();
		dataIntent.setAction("com.ooba.ContactUsActivity");
		dataIntent.putExtra("natureOfEnquiry", "Prequalify Now");
		context.sendBroadcast(dataIntent);
	}

	public String getString(int no) {
		String x = "";
		if (no >= 10)
			return no + "";
		else {
			return "0" + no;
		}
	}

	public void showMessage(String message) {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
		dlgAlert.setTitle("Error");
		dlgAlert.setMessage(message);
		dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}

	public double convertToDouble(String string) {
		return Double.parseDouble(string);
	}

	public int convertToInt(String string) {
		return Integer.parseInt(string);
	}

	public Date dateFormatYYMMDDCalendar(String dateString) {

		try {
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dt.parse(dateString);
			return date;
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>>" + e.getMessage());
		}
		return null;
	}

	private void clearText(EditText propertyvalueEditText,
			EditText depositRequiredEditText,
			EditText currentSavingAmountEditText,
			EditText savingInterestRateEditText) {
		propertyvalueEditText.setText("");
		depositRequiredEditText.setText("");
		currentSavingAmountEditText.setText("");
		savingInterestRateEditText.setText("");
	}
}
