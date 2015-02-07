package com.ooba;

import java.util.ArrayList;

import za.co.ooba.calculator.BondCalculationService;
import za.co.ooba.calculator.OobaCalculator;
import za.co.ooba.calculator.repayment.BondRepaymentCalculationDTO;
import za.co.ooba.calculator.repayment.BondRepaymentCalculationService;
import za.co.ooba.calculator.transfer.cost.BondTransferCostCalculationDTO;
import za.co.ooba.calculator.transfer.cost.BondTransferCostCalculationService;

import com.ooba.entity.NatureOfEnquiry;
import com.ooba.util.DialogList;
import com.ooba.util.DoubleFormaterClass;
import com.ooba.util.OobaFormattingInputFields;
import com.ooba.util.OobaGoogleAnalytics;
import com.ooba.util.OobaValidation;

import android.R.bool;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView.OnEditorActionListener;

public class BondAndTransferCostCalculator {
	Context context;

	EditText purchasePriceEditText;
	EditText loanAmountEditText;
	EditText natureofStatusEditText;
	EditText sellerRegisterdEditText;
	EditText statusOfPurchaseEditText;
	LinearLayout layoutAffordability;
	Button calculate;
	Button preQualify;
	TextView prequalifyTextView;

	TextView bondRegistrationCostTextView;
	TextView bonInitiationFeeTextView;
	TextView bondOtherCostsTextView;
	TextView totalBondCostsTextView;
	TextView transferCostTextView;
	TextView transferDutyTextView;
	TextView transferOtherCostsTextView;
	TextView totalTransferCostsTextView;
	TextView totalCostsTextView;

	public BondAndTransferCostCalculator(Context context) {
		this.context = context;
	}

	public void createLayout() {
		try {
			new OobaGoogleAnalytics(context).sendTrackedEvent("Bond And Transfer Cost Calculator Page", "Bond and Transfer Cost Calcualtor", "Bond and Transfer Cost Calcualtor");					
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}
		
		FrameLayout framelayout = (FrameLayout) ((CalculatorsActivity) context)
				.findViewById(R.id.layout_basecontainer);
		framelayout.removeAllViews();
		framelayout.addView(((CalculatorsActivity) context).getLayoutInflater()
				.inflate(R.layout.bond_transfer_cost_calc_layout, null));

		purchasePriceEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_bondtransfercalc_purchase_price);
		loanAmountEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_bondtransfercalc_loan_amount);
		natureofStatusEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_bondtransfercalc_natureof_status);
		sellerRegisterdEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_bondtransfercalc_seller_registred_for_vat);
		statusOfPurchaseEditText = (EditText) ((CalculatorsActivity) context)
				.findViewById(R.id.edittext_bondtransfercalc_ststus_of_purchase);
		calculate = (Button) ((CalculatorsActivity) context)
				.findViewById(R.id.button_bondandtransfer_calculate);
		preQualify = (Button) ((CalculatorsActivity) context)
				.findViewById(R.id.button_bondandtransfer_prequlify);
		layoutAffordability = (LinearLayout) framelayout
				.findViewById(R.id.layout_affordability);
		prequalifyTextView = (TextView) framelayout
				.findViewById(R.id.textview_bond_transfer_prequalify);

		bondRegistrationCostTextView = (TextView) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_bondtransfercalc_bond_registration_cost);
		bonInitiationFeeTextView = (TextView) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_bondtransfercalc_bond_initiation_fee);
		bondOtherCostsTextView = (TextView) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_bondtransfercalc_post_petties_1);
		totalBondCostsTextView = (TextView) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_bondtransfercalc_bond_total_bond_registration_cost);
		transferCostTextView = (TextView) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_bondtransfercalc_bond_property_transfer_costs);
		transferDutyTextView = (TextView) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_bondtransfercalc_bond_transfer_duty);
		transferOtherCostsTextView = (TextView) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_bondtransfercalc_bond_post_petties_2);
		totalTransferCostsTextView = (TextView) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_bondtransfercalc_bond_total_transfer_cost);
		totalCostsTextView = (TextView) ((CalculatorsActivity) context)
				.findViewById(R.id.textview_bondtransfercalc_bond_total_cost);

		layoutAffordability.setVisibility(View.GONE);
		preQualify.setVisibility(View.INVISIBLE);
		natureofStatusEditText.setText("Freehold");
		
		new OobaFormattingInputFields(context).oobaThousandFormatter(purchasePriceEditText);
		new OobaFormattingInputFields(context).oobaThousandFormatter(loanAmountEditText);
		
		natureofStatusEditText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				natureofStatusEditText.setFocusableInTouchMode(true);
				natureofStatusEditText.requestFocus();

				ArrayList<NatureOfEnquiry> arrayList = new ArrayList<NatureOfEnquiry>();
				arrayList.add(new NatureOfEnquiry("Freehold"));
				arrayList.add(new NatureOfEnquiry("Sectional"));

				DialogList list = new DialogList(context);
				list.showList(arrayList, natureofStatusEditText, sellerRegisterdEditText);
				
			}
		});
		sellerRegisterdEditText.setText("No");
		sellerRegisterdEditText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sellerRegisterdEditText.setFocusableInTouchMode(true);
				sellerRegisterdEditText.requestFocus();
				ArrayList<NatureOfEnquiry> arrayList = new ArrayList<NatureOfEnquiry>();
				arrayList.add(new NatureOfEnquiry("Yes"));
				arrayList.add(new NatureOfEnquiry("No"));

				DialogList list = new DialogList(context);
				list.showList(arrayList, sellerRegisterdEditText, statusOfPurchaseEditText);

			}
		});

		statusOfPurchaseEditText.setText("Natural Person");
		statusOfPurchaseEditText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				statusOfPurchaseEditText.setFocusableInTouchMode(true);
				statusOfPurchaseEditText.requestFocus();
				ArrayList<NatureOfEnquiry> arrayList = new ArrayList<NatureOfEnquiry>();
				arrayList.add(new NatureOfEnquiry("Natural Person"));
				arrayList.add(new NatureOfEnquiry("Company"));
				arrayList.add(new NatureOfEnquiry("Closed Corporation"));
				arrayList.add(new NatureOfEnquiry("Trust"));

				DialogList list = new DialogList(context);
				list.showList(arrayList, statusOfPurchaseEditText, calculate);

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

		loanAmountEditText
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {

						if (actionId == EditorInfo.IME_ACTION_NEXT
								|| actionId == EditorInfo.IME_ACTION_DONE) {
							natureofStatusEditText.performClick();
						}
						return false;
					}
				});
		calculate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					new OobaGoogleAnalytics(context).sendTrackedEvent("Button Click", "Bond and Transfer Cost Calculator", calculate.getText().toString());
				} catch (Exception e) {
					System.out.println(">>>Exception>>>" + e.toString()
							+ ">>>Message>>" + e.getMessage());
				}
				
				if (new OobaValidation(context).bondTransferCostValidation(
						purchasePriceEditText, loanAmountEditText) == true) {
					OobaCalculator oobaCalculator = new OobaCalculator();
					BondTransferCostCalculationDTO bondTransferCostCalculationDTO = new BondTransferCostCalculationDTO();

					double purchasePriceDouble = convertToDouble(new OobaFormattingInputFields(context).oobaGetThousandFormatter(purchasePriceEditText));
					double loanAmountDouble = convertToDouble(new OobaFormattingInputFields(context).oobaGetThousandFormatter(loanAmountEditText));
					Boolean sellerRegisterdBoolean = Boolean
							.parseBoolean(sellerRegisterdEditText.getText()
									.toString());

					String natureofStatus = natureofStatusEditText.getText()
							.toString();
					String statusOfPurchase = statusOfPurchaseEditText
							.getText().toString();

					try {
						bondTransferCostCalculationDTO = oobaCalculator
								.calculateBondAndTransferCostAmount(
										purchasePriceDouble, loanAmountDouble,
										natureofStatus, sellerRegisterdBoolean,
										statusOfPurchase);

						if (bondTransferCostCalculationDTO.isError()) {
							showMessage(bondTransferCostCalculationDTO
									.getErrors());

						} else {
							bondRegistrationCostTextView.setText("R "
									+ DoubleFormaterClass
											.priceWithDecimal(bondTransferCostCalculationDTO
													.getBondRegistrationCost()));
							bonInitiationFeeTextView.setText("R "
									+ DoubleFormaterClass
											.priceWithDecimal(bondTransferCostCalculationDTO
													.getBondInitiationFee()));
							bondOtherCostsTextView.setText("R "
									+ DoubleFormaterClass
											.priceWithDecimal(bondTransferCostCalculationDTO
													.getBondOtherCosts()));
							totalBondCostsTextView.setText("R "
									+ DoubleFormaterClass
											.priceWithDecimal(bondTransferCostCalculationDTO
													.getTotalBondCosts()));
							transferCostTextView.setText("R "
									+ DoubleFormaterClass
											.priceWithDecimal(bondTransferCostCalculationDTO
													.getTransferCost()));
							transferDutyTextView.setText("R "
									+ DoubleFormaterClass
											.priceWithDecimal(bondTransferCostCalculationDTO
													.getTransferDuty()));
							transferOtherCostsTextView.setText("R "
									+ DoubleFormaterClass
											.priceWithDecimal(bondTransferCostCalculationDTO
													.getTransferOtherCosts()));
							totalTransferCostsTextView.setText("R "
									+ DoubleFormaterClass
											.priceWithDecimal(bondTransferCostCalculationDTO
													.getTotalTransferCosts()));
							totalCostsTextView.setText(" R "
									+ DoubleFormaterClass
											.priceWithDecimal(bondTransferCostCalculationDTO
													.getTotalCosts()));

							calculate.setText("RECALCULATE");
							preQualify.setVisibility(View.VISIBLE);
							layoutAffordability.setVisibility(View.VISIBLE);
							prequalifyTextView.setFocusableInTouchMode(true);
							prequalifyTextView.requestFocus();
							layoutAffordability.setVisibility(View.VISIBLE);

						}
					} catch (Exception e) {
						System.out.println(">>>Exception>>>" + e.toString()
								+ ">>>Message>>>" + e.getMessage());
					}
				}

			}
		});
	}

	
	public void showContactUs(){
		try {
			new OobaGoogleAnalytics(context).sendTrackedEvent("Button Click", "Bond and Transfer Cost Calculator", "Prequalify Now");
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}
		
		BaseTabActivity.tabHost.getTabWidget().removeView(
				BaseTabActivity.tabHost.getTabWidget()
						.getChildTabViewAt(3));
		TabSpec spec = BaseTabActivity.tabHost.newTabSpec("contact");
		View tabIndicator = LayoutInflater.from(context).inflate(
				R.layout.tab_indicator,
				BaseTabActivity.tabHost.getTabWidget(), false);
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
	private void clearText(EditText purchasePriceEditText,
			EditText loanAmountEditText) {
		purchasePriceEditText.setText("");
		loanAmountEditText.setText("");
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
