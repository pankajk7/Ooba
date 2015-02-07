package com.ooba;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import za.co.ooba.calculator.BondCalculationService;
import za.co.ooba.calculator.OobaCalculator;
import za.co.ooba.calculator.affordability.AffordabilityCalculationDTO;
import za.co.ooba.calculator.repayment.BondRepaymentCalculationDTO;

import com.ooba.Adapter.NatureOfEnquiryBaseAdapter;
import com.ooba.entity.NatureOfEnquiry;
import com.ooba.util.DoubleFormaterClass;
import com.ooba.util.OobaFormattingInputFields;
import com.ooba.util.OobaGoogleAnalytics;
import com.ooba.util.OobaValidation;

import android.R.integer;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.MonthDisplayHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.View.OnClickListener;
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

public class AffordabilityCalculatorActivity {

	TextView informationTextView;
	TextView repaymentamountTextView;
	TextView grossincomeTextView;
	TextView textview_affordability_affordability_title;
	Context context;

	TextView prequalifyTextView;
	EditText grossMonthlyIncomeEditText;
	EditText nettMonthlyIncomeEditText;
	EditText totalMonthlyExpenseEditText;
	EditText interestRateEditText;

	TextView nettSurplusTextView;
	EditText yearToRepayTextView;

	Button prequalifyButton;
	Button calculateRecalculateButton;

	LinearLayout layoutAffordability;

	boolean isClicked = false;
	ColorStateList textcolor;

	public AffordabilityCalculatorActivity(Context context) {
		this.context = context;
	}

	public void createLayout() {

		try {
			new OobaGoogleAnalytics(context).sendTrackedEvent(
					"Affordability Calculator Page",
					"Affordability Calculator", "Affordability Calculator");
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}

		FrameLayout framelayout = (FrameLayout) ((CalculatorsActivity) context)
				.findViewById(R.id.layout_basecontainer);
		framelayout.removeAllViews();
		framelayout.addView(((CalculatorsActivity) context).getLayoutInflater()
				.inflate(R.layout.activity_affordability_calculator, null));

		textview_affordability_affordability_title = informationTextView = (TextView) framelayout
				.findViewById(R.id.textview_affordability_affordability_title);

		informationTextView = (TextView) framelayout
				.findViewById(R.id.affordcalc_informationnote);
		repaymentamountTextView = (TextView) framelayout
				.findViewById(R.id.textview_affordcalc_repaymentamount);
		grossincomeTextView = (TextView) framelayout
				.findViewById(R.id.textview_affordcalc_grossincome);
		prequalifyButton = (Button) framelayout
				.findViewById(R.id.button_afford_calc_prequlify);
		grossMonthlyIncomeEditText = (EditText) framelayout
				.findViewById(R.id.edittext_afford_calc_gross_montlyincome);
		nettMonthlyIncomeEditText = (EditText) framelayout
				.findViewById(R.id.edittext_afford_calc_nett_montlyincome);
		totalMonthlyExpenseEditText = (EditText) framelayout
				.findViewById(R.id.edittext_afford_calc_total_montlyexpenses);
		nettSurplusTextView = (TextView) framelayout
				.findViewById(R.id.textview_afford_calc_nett_surplusincome);
		prequalifyTextView = (TextView) framelayout
				.findViewById(R.id.textview_affordability_calculator_prequalify);
		calculateRecalculateButton = (Button) framelayout
				.findViewById(R.id.button_afford_calc_calculate);
		layoutAffordability = (LinearLayout) framelayout
				.findViewById(R.id.layout_affordability);

		yearToRepayTextView = (EditText) framelayout
				.findViewById(R.id.textview_affordability_yeartorepay);
		interestRateEditText = (EditText) framelayout
				.findViewById(R.id.edittext_afford_calc_interestrate);

		prequalifyButton.setVisibility(View.INVISIBLE);
		layoutAffordability.setVisibility(View.GONE);

		nettSurplusTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getNettSurplusIncome();
			}
		});

		double ir = CalculatorsActivity.interestRate;
		if (Double.compare(ir, 0.0) < 0 || Double.compare(ir, 0.0) == 0) {
			interestRateEditText.setText("9.0" + "%");
		} else {
			interestRateEditText.setText(String
					.valueOf(CalculatorsActivity.interestRate) + "%");
		}

		nettSurplusTextView.setHint("R");
		textcolor = nettSurplusTextView.getHintTextColors();

		new OobaFormattingInputFields(context, calculateRecalculateButton)
				.oobaInterestRateFormatter(interestRateEditText);

		new OobaFormattingInputFields(context, nettMonthlyIncomeEditText)
				.oobaThousandFormatter(grossMonthlyIncomeEditText);
		new OobaFormattingInputFields(context, totalMonthlyExpenseEditText)
				.oobaThousandFormatter(nettMonthlyIncomeEditText);
		new OobaFormattingInputFields(context, nettSurplusTextView)
				.oobaThousandFormatter(nettMonthlyIncomeEditText);
		new OobaFormattingInputFields(context)
				.oobaThousandFormatter(totalMonthlyExpenseEditText);

		interestRateEditText.setImeOptions(EditorInfo.IME_ACTION_GO);

		nettMonthlyIncomeEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable mEdit) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				getNettSurplusIncome();
			}
		});

		totalMonthlyExpenseEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable mEdit) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				getNettSurplusIncome();
			}
		});

		nettMonthlyIncomeEditText
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_NEXT) {
							getNettSurplusIncome();
						}
						return false;
					}
				});

		totalMonthlyExpenseEditText
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_NEXT) {
							getNettSurplusIncome();
							showYearsToRepayList();
						}
						return false;
					}
				});

		String font = "<font color='#69bd27'>";
		repaymentamountTextView.setText(Html.fromHtml("= <b>" + font
				+ "R 9000.00" + "</font></b>"));
		grossincomeTextView.setText(Html.fromHtml("= <b>" + font + "R 9000.00"
				+ "</font></b>"));
		informationTextView
				.setText(Html
						.fromHtml("<B>Note:</B> This calculation is based on general lender affordability credit guide-lines of 30% instalment to gross income and on your disposable income."));
		yearToRepayTextView.setText("20");
		yearToRepayTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showYearsToRepayList();
			}
		});

		nettSurplusTextView
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_NEXT) {
							showYearsToRepayList();
						}
						return false;
					}
				});

		calculateRecalculateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					new OobaGoogleAnalytics(context).sendTrackedEvent(
							"Button Click", "Affordability Calculator",
							calculateRecalculateButton.getText().toString());
				} catch (Exception e) {
					System.out.println(">>>Exception>>>" + e.toString()
							+ ">>>Message>>" + e.getMessage());
				}

				if (new OobaValidation(context).affordablityCalc(
						grossMonthlyIncomeEditText, nettMonthlyIncomeEditText,
						totalMonthlyExpenseEditText, nettSurplusTextView,
						interestRateEditText) == true) {

					OobaCalculator oobaCalculator = new OobaCalculator();
					AffordabilityCalculationDTO affordabilityCalculationDTO = new AffordabilityCalculationDTO();

					double grossMonthlyDouble = convertToDouble(new OobaFormattingInputFields(
							context)
							.oobaGetThousandFormatter(grossMonthlyIncomeEditText));
					double nettMonthlyIncomeDouble = convertToDouble(new OobaFormattingInputFields(
							context)
							.oobaGetThousandFormatter(nettMonthlyIncomeEditText));
					double totalMonthlyExpenseDouble = convertToDouble(new OobaFormattingInputFields(
							context)
							.oobaGetThousandFormatter(totalMonthlyExpenseEditText));
					String temp = interestRateEditText.getText().toString()
							.trim();
					temp = temp.replace("%", "");
					double interestRateDouble = convertToDouble(temp);
					int yearToRepay = convertToInt(yearToRepayTextView
							.getText().toString().trim());

					try {
						affordabilityCalculationDTO = oobaCalculator
								.calculateAffordability(grossMonthlyDouble,
										nettMonthlyIncomeDouble,
										totalMonthlyExpenseDouble,
										interestRateDouble, yearToRepay);

						if (affordabilityCalculationDTO.isError()) {
							showMessage(affordabilityCalculationDTO.getErrors());
						} else {
							prequalifyButton.setVisibility(View.VISIBLE);
							layoutAffordability.setVisibility(View.VISIBLE);
							calculateRecalculateButton.setText("RECALCULATE");
							prequalifyTextView.setFocusableInTouchMode(true);
							prequalifyTextView.requestFocus();

							BondRepaymentCalculationDTO bondRepaymentCalculationDTO = affordabilityCalculationDTO
									.getBondRepaymentCalculationDTO();

							repaymentamountTextView.setText(" R "
									+ DoubleFormaterClass
											.priceWithDecimal(bondRepaymentCalculationDTO
													.getTotalMonthlyRepaymentAmount()));
							grossincomeTextView.setText(" R "
									+ DoubleFormaterClass
											.priceWithDecimal(affordabilityCalculationDTO
													.getQualifiedAmountGrossIncome()));
						}
					} catch (Exception e) {
						System.out.println(">>>Exception>>>" + e.toString()
								+ ">>>Message>>>" + e.getMessage());
					}
				}
			}
		});

		prequalifyButton.setOnClickListener(new OnClickListener() {

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
	}

	public void showContactUs() {
		try {
			new OobaGoogleAnalytics(context).sendTrackedEvent("Button Click",
					"Affordability Calculator", "Prequalify Now");
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

	public void getNettSurplusIncome() {
		if (!nettMonthlyIncomeEditText.getText().toString().equals("")
				&& !totalMonthlyExpenseEditText.getText().toString().equals("")) {
			try {
				long netincome = Long.parseLong(new OobaFormattingInputFields(
						context)
						.oobaGetThousandFormatter(nettMonthlyIncomeEditText));
				long expense = Long.parseLong(new OobaFormattingInputFields(
						context)
						.oobaGetThousandFormatter(totalMonthlyExpenseEditText));
				long nettSurplusIncome = netincome - expense;
				if (nettSurplusIncome < 0) {
					nettSurplusTextView.setText("");
					nettSurplusTextView
							.setHint("Nett monthly income must be greater than monthly expenses.");
					nettSurplusTextView.setTextSize(10);
					nettSurplusTextView.setHintTextColor(Color.RED);
					return;
				}
				nettSurplusTextView.setTextSize(18);
				String s = String.valueOf(nettSurplusIncome);
				double d = Double.parseDouble(s);
				s = DoubleFormaterClass.priceWithDecimal(d);
				s = s.replace(",", " ");
				s = s.replace(".00", "");
				String n = "R " + s;
				if (n.equals("R ")) {
					;
					n = "R 0";
				}
				nettSurplusTextView.setText(n);
			} catch (Exception ex) {
				System.out.println("Error" + ex.toString());
			}
		}else{
			nettSurplusTextView.setText("");
			nettSurplusTextView.setHintTextColor(textcolor);
			nettSurplusTextView.setTextSize(16);
			nettSurplusTextView.setHint("R");
		}
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
				yearToRepayTextView.setText(stringList.get(position).listString);
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

}
