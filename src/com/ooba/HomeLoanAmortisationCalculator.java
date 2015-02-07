package com.ooba;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import za.co.ooba.calculator.BondCalculationService;
import za.co.ooba.calculator.OobaCalculator;
import za.co.ooba.calculator.amortization.AmortizationCalculationDTO;
import za.co.ooba.calculator.amortization.AmortizationCalculationService;
import za.co.ooba.calculator.deposit.saving.DepositSavingCalculationDTO;
import za.co.ooba.calculator.deposit.saving.DepositSavingCalculationService;
import za.co.ooba.calculator.dto.FormattedPaymentPeriodDTO;
import za.co.ooba.calculator.dto.PaymentPeriodDTO;

import com.ooba.Adapter.ExpandableListAdapter;
import com.ooba.Adapter.GridListAdapter;
import com.ooba.Adapter.NatureOfEnquiryBaseAdapter;
import com.ooba.entity.AmmortisationHeader;
import com.ooba.entity.GridRowData;
import com.ooba.entity.NatureOfEnquiry;
import com.ooba.util.DialogList;
import com.ooba.util.DoubleFormaterClass;
import com.ooba.util.ExpandableHeightListView;
import com.ooba.util.OobaFormattingInputFields;
import com.ooba.util.OobaGoogleAnalytics;
import com.ooba.util.OobaValidation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView.OnEditorActionListener;

public class HomeLoanAmortisationCalculator {

	Context context;
	EditText numberOfMonthEditText;
	EditText interestRateEditText;
	EditText totalAmountofLoanEditText;
	EditText fixedInterestRateEditText;
	Button calculate;
	Button preQualify;
	EditText noOfMonthsOfFixedTermTextView;
	TextView totalcost;
	TextView interestbreakdown;
	TextView prequalifyTextView;
	LinearLayout homeLoanAmortisationLayout;
	ScrollView scrollView;
	ExpandableListView exapandGrid;
	ExpandableListAdapter listAdapter;

	boolean isClicked = false;

	public HomeLoanAmortisationCalculator(Context context) {
		this.context = context;

	}

	public void createLayout() {
		try {
			new OobaGoogleAnalytics(context).sendTrackedEvent("Home Load Amotisation Calcualtor Page", "Home Load Amotisation Calcualtor", "Home Load Amotisation Calcualtor");
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}
		FrameLayout framelayout = (FrameLayout) ((CalculatorsActivity) context)
				.findViewById(R.id.layout_basecontainer);
		framelayout.removeAllViews();
		framelayout.addView(((CalculatorsActivity) context).getLayoutInflater()
				.inflate(R.layout.home_loan_amortisation_calc, null));

		exapandGrid = (ExpandableListView) ((CalculatorsActivity) context)
				.findViewById(R.id.expandableview_homeloanammortisation_grid_list);

		numberOfMonthEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_homeloan_ammorisation_nofmonths);
		interestRateEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_homeloan_ammorisation_interestrate);
		totalAmountofLoanEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_homeloan_ammorisation_totalamount_loan);
		noOfMonthsOfFixedTermTextView = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_homeloan_ammorisation_nofmonths_of_fixeditem);
		fixedInterestRateEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_homeloan_ammorisation_fixed_interestrate);
		homeLoanAmortisationLayout = (LinearLayout) ((CalculatorsActivity) context)
				.findViewById(R.id.layout_home_loan_amortisation);
		calculate = (Button) ((CalculatorsActivity) context)
				.findViewById(R.id.button_homeloan_ammorisation_calculate);
		preQualify = (Button) ((CalculatorsActivity) context)
				.findViewById(R.id.button_homeloan_ammorisation_prequalify);

		prequalifyTextView = (TextView) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_affordability_calculator_prequalify);
		scrollView=(ScrollView)((CalculatorsActivity) context).findViewById(R.id.ScrollView);
		totalcost = (TextView) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_bondtransfercalc_bond_total_cost);
		interestbreakdown = (TextView) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_bondtransfercalc_bond_interest_breakdown);
		preQualify.setVisibility(View.GONE);
		totalcost.setVisibility(View.GONE);
		interestbreakdown.setVisibility(View.GONE);

		double ir = CalculatorsActivity.interestRate;
		if (Double.compare(ir, 0.0) < 0 || Double.compare(ir, 0.0) == 0) {
			interestRateEditText.setText("9.0" + "%");
		} else {
			interestRateEditText.setText(String
					.valueOf(CalculatorsActivity.interestRate) + "%");
		}
		
		fixedInterestRateEditText.setText("%");

		new OobaFormattingInputFields(context)
				.oobaInterestRateFormatterNotLast(interestRateEditText);
		new OobaFormattingInputFields(context)
				.oobaThousandFormatter(totalAmountofLoanEditText);
		new OobaFormattingInputFields(context, calculate)
				.oobaInterestRateFormatter(fixedInterestRateEditText);
		
		
		totalAmountofLoanEditText
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_NEXT) {
							showNumberOfMonth();
						}

						return true;
					}
				});

		exapandGrid.setOnGroupExpandListener(new OnGroupExpandListener() {
			int previousGroup = -1;

			@Override
			public void onGroupExpand(int groupPosition) {
				if (groupPosition != previousGroup)
					exapandGrid.collapseGroup(previousGroup);
				previousGroup = groupPosition;
				setListViewHeightBasedOnChildren(exapandGrid);
				
			}
			
		});

		ArrayList<AmmortisationHeader> header = new ArrayList<AmmortisationHeader>();
		AmmortisationHeader headerItem;
		ArrayList<ArrayList<GridRowData>> rows = new ArrayList<ArrayList<GridRowData>>();
		;
		ArrayList<GridRowData> item;
		for (int i = 0; i < 4; i++) {

			item = new ArrayList<GridRowData>();
			for (int j = 0; j <= 5; j++) {

				item.add(new GridRowData(j + "", (200 * j) + "",
						(400 * j) + "", (j * 500) + "", (j * 800) + ""));
			}

			headerItem = new AmmortisationHeader("Year " + i, item);
			header.add(headerItem);

		}

		
		GridListAdapter gridlist = new GridListAdapter(context, header, rows);
		exapandGrid.setAdapter(gridlist);

		
		
		noOfMonthsOfFixedTermTextView.setText("0");
		noOfMonthsOfFixedTermTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showNumberOfMonth();
			}
		});

		calculate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					new OobaGoogleAnalytics(context).sendTrackedEvent("Button Click", "Home Loan Amortisation Calculator", calculate.getText().toString());
				} catch (Exception e) {
					System.out.println(">>>Exception>>>" + e.toString()
							+ ">>>Message>>" + e.getMessage());
				}

				if (new OobaValidation(context)
						.homeLoanAmmortisationValidation(numberOfMonthEditText,
								interestRateEditText,
								totalAmountofLoanEditText,
								fixedInterestRateEditText) == true) {

					OobaCalculator oobaCalculator = new OobaCalculator();
					AmortizationCalculationDTO amortizationCalculationDTO = new AmortizationCalculationDTO();
					List<FormattedPaymentPeriodDTO> formattedPaymentPeriodDTOList = new ArrayList<FormattedPaymentPeriodDTO>();

					int numberOfMonth = convertToInt(numberOfMonthEditText
							.getText().toString());

					double totalAmountofLoanDouble = convertToDouble(new OobaFormattingInputFields(
							context)
							.oobaGetThousandFormatter(totalAmountofLoanEditText));

					int nnoOfMonthsOfFixedTerm = convertToInt(noOfMonthsOfFixedTermTextView
							.getText().toString().trim());

					String temp1 = fixedInterestRateEditText.getText()
							.toString().trim();
					double fixedInterestRateDouble;
					temp1 = temp1.replace("%", "");
					if (temp1.equals("")) {
						fixedInterestRateDouble = 0;
					} else {						
						fixedInterestRateDouble = convertToDouble(temp1);
					}

					String temp = interestRateEditText.getText().toString()
							.trim();
					temp = temp.replace("%", "");
					double interestRateDouble = convertToDouble(temp);

					try {
						amortizationCalculationDTO = oobaCalculator
								.calculateAmortizationAmount(
										totalAmountofLoanDouble, numberOfMonth,
										interestRateDouble,
										nnoOfMonthsOfFixedTerm,
										fixedInterestRateDouble);

						if (amortizationCalculationDTO.isError()) {
							showMessage(amortizationCalculationDTO.getErrors());
						} else {
							formattedPaymentPeriodDTOList = amortizationCalculationDTO
									.getFormattedPaymentPeriods();

							totalcost.setText(new OobaFormattingInputFields(
									context)
									.stringThousandFormatting(DoubleFormaterClass
											.priceWithDecimal(amortizationCalculationDTO
													.getTotalInterest())));

							ArrayList<AmmortisationHeader> header = new ArrayList<AmmortisationHeader>();
							AmmortisationHeader headerItem;
							ArrayList<ArrayList<GridRowData>> rows = new ArrayList<ArrayList<GridRowData>>();
							ArrayList<GridRowData> item;
							DecimalFormat df = new DecimalFormat("#.00");
							double nom = Double
									.parseDouble(numberOfMonthEditText
											.getText().toString().trim());
							double y = (nom / 12.00);
							double yy = Math.ceil(y);
							long noy = Math.round(yy);
							int no_of_month = Integer
									.parseInt(numberOfMonthEditText.getText()
											.toString().trim());
							int elasped = 0;
							for (int i = 0; i < noy; i++) {

								item = new ArrayList<GridRowData>();
								for (int j = 0; j < 12; j++) {

									if (elasped >= no_of_month)
										break;
									double payment = formattedPaymentPeriodDTOList
											.get(elasped).getPayment();
									double interestAmount = formattedPaymentPeriodDTOList
											.get(elasped).getInterest();

									double balance = formattedPaymentPeriodDTOList
											.get(elasped)
											.getOutstandingBalance();
									String bal = df.format(balance);
									if (bal.equals(".00"))
										bal = "0";

									double central_reduction = payment
											- interestAmount;
									item.add(new GridRowData(String
											.valueOf(elasped+1), String.valueOf(df
											.format(payment)) + "", String
											.valueOf(df.format(interestAmount))
											+ "", String.valueOf(df
											.format(central_reduction)) + "",
											bal + ""));
									elasped++;
								}
								headerItem = new AmmortisationHeader("Year "
										+ (i + 1), item);
								header.add(headerItem);
							}

							
							GridListAdapter gridlist = new GridListAdapter(
									context, header, rows);
							exapandGrid.setAdapter(gridlist);
							
							exapandGrid.expandGroup(0);
							
							amortizationCalculationDTO.getTotalInterest();
							calculate.setText("RECALCULATE");
							preQualify.setVisibility(View.VISIBLE);
							totalcost.setVisibility(View.VISIBLE);
							interestbreakdown.setVisibility(View.VISIBLE);
							homeLoanAmortisationLayout
									.setVisibility(View.VISIBLE);
							LinearLayout lin=(LinearLayout)((CalculatorsActivity)context).findViewById(R.id.layout_home_loan_amortisation);
							lin.setFocusableInTouchMode(true);
							lin.requestFocus();
							
							
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
	}
	
		
	 public void setListViewHeightBasedOnChildren(ListView listView) {
	        ListAdapter listAdapter = listView.getAdapter(); 
	        if (listAdapter == null) {
	            return;
	        }

	        int totalHeight = 0;
	        for (int i = 0; i < listAdapter.getCount(); i++) {
	            View listItem = listAdapter.getView(i, null, listView);
	            listItem.measure(0, 0);
	            totalHeight += listItem.getMeasuredHeight();
	        }

	        ViewGroup.LayoutParams params = listView.getLayoutParams();
	        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	        listView.setLayoutParams(params);
	        listView.requestLayout();
	    }
	 


	public void showContactUs() {
		try {
			new OobaGoogleAnalytics(context).sendTrackedEvent("Button Click", "Home Loan Amortisation Calculator", "Prequalify Now");
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

	public void showNumberOfMonth() {

		final Dialog dialog1 = new Dialog(context);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog1.setContentView(R.layout.nature_of_query_layout);
		dialog1.setCancelable(false);
		ListView stringListView = (ListView) dialog1
				.findViewById(R.id.listview_nature_of_enquiry_listiview);
		final ArrayList<NatureOfEnquiry> stringList = new ArrayList<NatureOfEnquiry>();
		NatureOfEnquiry lists;
		for (int i = 0; i < 25; i++) {
			lists = new NatureOfEnquiry(i + "");

			stringList.add(lists);
		}

		NatureOfEnquiryBaseAdapter adapter = new NatureOfEnquiryBaseAdapter(
				context, stringList);
		stringListView.setAdapter(adapter);

		stringListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				noOfMonthsOfFixedTermTextView.setText(stringList.get(position).listString);
				fixedInterestRateEditText.setFocusableInTouchMode(true);
				fixedInterestRateEditText.requestFocus();
				dialog1.dismiss();
			}

		});

		dialog1.show();

		dialog1.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				fixedInterestRateEditText.post(new Runnable() {
					public void run() {
						fixedInterestRateEditText.requestFocusFromTouch();
						InputMethodManager lManager = (InputMethodManager) context
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						lManager.showSoftInput(fixedInterestRateEditText, 0);
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
