package com.ooba;

import java.util.ArrayList;

import za.co.ooba.calculator.BondCalculationService;
import za.co.ooba.calculator.OobaCalculator;
import za.co.ooba.calculator.repayment.BondRepaymentCalculationDTO;
import za.co.ooba.calculator.repayment.BondRepaymentCalculationService;

import com.ooba.Adapter.NatureOfEnquiryBaseAdapter;
import com.ooba.entity.NatureOfEnquiry;
import com.ooba.util.DoubleFormaterClass;
import com.ooba.util.OobaFormattingInputFields;
import com.ooba.util.OobaGoogleAnalytics;
import com.ooba.util.OobaValidation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.YuvImage;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView.OnEditorActionListener;

public class BondRepaymentCalculator {
	EditText purchasePriceEditText;
	EditText repaymentPeriodEditText;
	EditText interestRateEditText;
	EditText depositEditText;
	Context context;
	LinearLayout layoutAffordability;
	Button calculate;
	Button preQualify;
	TextView prequalifyTextView;

	TextView totalMonthlyRepaymentTextView;
	TextView totalLoanAmountTextView;

	boolean isClicked = false;

	public BondRepaymentCalculator(Context context) {

		this.context = context;
	}

	public void createLayout() {
		try {
			new OobaGoogleAnalytics(context).sendTrackedEvent("Bond Repayment Calculator Page", "Bond Repayment Calculator", "Bond Repayment Calculator");					
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}
		FrameLayout framelayout = (FrameLayout) ((CalculatorsActivity) context)
				.findViewById(R.id.layout_basecontainer);
		framelayout.removeAllViews();
		framelayout.addView(((CalculatorsActivity) context).getLayoutInflater()
				.inflate(R.layout.bond_repayment_calc, null));
		purchasePriceEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_bond_repayment_purchase_price);
		repaymentPeriodEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_bond_repayment_repayment_period);
		interestRateEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_bond_repayment_interest_period);
		depositEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_bond_repayment_deposit);
		layoutAffordability = (LinearLayout) framelayout
				.findViewById(R.id.layout_affordability);
		calculate = (Button) ((CalculatorsActivity) context)
				.findViewById(R.id.button_bondrepayment_calculate);
		preQualify = (Button) ((CalculatorsActivity) context)
				.findViewById(R.id.button_bondrepayment_prequlify);
		prequalifyTextView = (TextView) framelayout
				.findViewById(R.id.textview_affordability_calculator_prequalify);
		totalMonthlyRepaymentTextView = (TextView) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_bondrepayment_repaymentamount);
		totalLoanAmountTextView = (TextView) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_bondrepayment_total_loan_amount);

		preQualify.setVisibility(View.INVISIBLE);
		layoutAffordability.setVisibility(View.GONE);

		double ir = CalculatorsActivity.interestRate;
		if (Double.compare(ir, 0.0) < 0 || Double.compare(ir, 0.0) == 0) {
			interestRateEditText.setText("9.0" + "%");
		} else {
			interestRateEditText.setText(String
					.valueOf(CalculatorsActivity.interestRate) + "%");
		}

		new OobaFormattingInputFields(context, depositEditText)
				.oobaInterestRateFormatterNotLast(interestRateEditText);
		new OobaFormattingInputFields(context, repaymentPeriodEditText)
				.oobaThousandFormatter(purchasePriceEditText);
		new OobaFormattingInputFields(context, calculate)
				.oobaThousandFormatterNextClick(depositEditText);

		interestRateEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		
		calculate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					new OobaGoogleAnalytics(context).sendTrackedEvent("Button Click", "Bond Repayment Calculator", calculate.getText().toString());
				} catch (Exception e) {
					System.out.println(">>>Exception>>>" + e.toString()
							+ ">>>Message>>" + e.getMessage());
				}

				OobaValidation x = new OobaValidation(context);
				if (x.BondRepaymentCalc(purchasePriceEditText,
						repaymentPeriodEditText, interestRateEditText,
						depositEditText) == true) {
					OobaCalculator oobaCalculator = new OobaCalculator();
					BondRepaymentCalculationDTO bondRepaymentCalculationDTO = new BondRepaymentCalculationDTO();

					double purchasePriceDouble = convertToDouble(new OobaFormattingInputFields(
							context)
							.oobaGetThousandFormatter(purchasePriceEditText));
					double depositDouble = convertToDouble(new OobaFormattingInputFields(
							context).oobaGetThousandFormatter(depositEditText));

					String temp = interestRateEditText.getText().toString()
							.trim();
					temp = temp.replace("%", "");

					double interestRateDouble = convertToDouble(temp);
					int repaymentPeriod = convertToInt(repaymentPeriodEditText
							.getText().toString().trim());

					try {
						bondRepaymentCalculationDTO = oobaCalculator
								.calculateBondRepayment(purchasePriceDouble,
										depositDouble, interestRateDouble,
										repaymentPeriod);

						if (bondRepaymentCalculationDTO.isError()) {
							showMessage(bondRepaymentCalculationDTO.getErrors());
						} else {

							totalMonthlyRepaymentTextView.setText(" R "
									+ DoubleFormaterClass
											.priceWithDecimal(bondRepaymentCalculationDTO
													.getTotalMonthlyRepaymentAmount()));
							totalLoanAmountTextView.setText(" R "
									+ DoubleFormaterClass
											.priceWithDecimal(bondRepaymentCalculationDTO
													.getTotalLoanAmount()));

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

		repaymentPeriodEditText.setText("20");
		repaymentPeriodEditText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showYearsToRepayList();
			}
		});

		purchasePriceEditText
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {

						if (actionId == EditorInfo.IME_ACTION_NEXT) {
							repaymentPeriodEditText.performClick();
						}
						return false;
					}
				});

	}

	public void showContactUs() {
		try {
			new OobaGoogleAnalytics(context).sendTrackedEvent("Button Click", "Bond Repayment Calculator", "Prequalify Now");
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

	public void showYearsToRepayList() {
		final Dialog dialog1 = new Dialog(context);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog1.setContentView(R.layout.nature_of_query_layout);
		dialog1.setCancelable(false);
		ListView stringListView = (ListView) dialog1
				.findViewById(R.id.listview_nature_of_enquiry_listiview);
		final ArrayList<NatureOfEnquiry> stringList = new ArrayList<NatureOfEnquiry>();
		stringList.add(new NatureOfEnquiry("5"));
		stringList.add(new NatureOfEnquiry("10"));
		stringList.add(new NatureOfEnquiry("15"));
		stringList.add(new NatureOfEnquiry("20"));
		stringList.add(new NatureOfEnquiry("25"));

		NatureOfEnquiryBaseAdapter adapter = new NatureOfEnquiryBaseAdapter(
				context, stringList);
		stringListView.setAdapter(adapter);

		stringListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				repaymentPeriodEditText.setText(stringList.get(position).listString);
				interestRateEditText.setFocusableInTouchMode(true);
				interestRateEditText.requestFocus();
				dialog1.dismiss();
			}

		});

		dialog1.show();

		dialog1.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				interestRateEditText.post(new Runnable() {
					public void run() {
						interestRateEditText.requestFocusFromTouch();
						InputMethodManager lManager = (InputMethodManager) context
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						lManager.showSoftInput(interestRateEditText, 0);
					}
				});

			}
		});
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

	private void clearText(EditText purchasePriceEditText,
			TextView repaymentPeriodEditText, EditText interestRateEditText,
			EditText depositEditText) {
		purchasePriceEditText.setText("");
		repaymentPeriodEditText.setText("");
		interestRateEditText.setText("");
		depositEditText.setText("");
	}
}
