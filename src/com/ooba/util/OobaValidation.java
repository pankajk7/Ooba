package com.ooba.util;

import android.R.bool;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class OobaValidation {
	Context context;
	public String backupString;

	public OobaValidation(Context context) {
		this.context = context;
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

	public void putMessage(EditText tb, String message, int color) {
		try {
			tb.setText("");
			if (tb.getText().toString().equals("") || tb.getText() == null) {
				tb.setHint(message);
				tb.setTextSize(16);
			}
			tb.setHintTextColor(color);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void putMessage(final EditText tb, String message, int color,
			boolean isClean) {
		try {

			tb.setText("");
			tb.setHint(tb.getText().toString() + "" + message);
			tb.setHintTextColor(color);
			tb.setTextSize(17);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean BondRepaymentCalc(EditText purchasePrice,
			TextView RepaymentPeriod, EditText InterestRate, EditText Deposit) {

		boolean isValid = true;
		String tempString = new OobaFormattingInputFields(context).oobaGetThousandFormatter(purchasePrice);
		if (tempString.trim().equals("")
				|| tempString.trim()
						.equals(null)) {
			putMessage(purchasePrice, "Please enter purchase price", Color.RED);
			isValid = false;
		} else {
			long pp = 0;
			try {
				pp = Long.parseLong(tempString);
			} catch (Exception e) {
				putMessage(purchasePrice, "Must be greater than 100000",
						Color.RED, true);
			}
			if (pp < 100000) {
				putMessage(purchasePrice, "Must be greater than 100000",
						Color.RED, true);
				isValid = false;
			}
		}
		
		String temp = InterestRate.getText().toString().trim();
		temp = temp.replace("%", "");
		if (temp.equals("")
				|| temp.equals(null)) {
			putMessage(InterestRate, "Please enter Interest Rate", Color.RED);
			isValid = false;
		} else {

			double ir = Double.parseDouble(temp);
			if (Double.compare(ir, 0.0) < 0 || Double.compare(ir, 0.0) == 0) {
				putMessage(InterestRate, "Interest rate must be greater than 0",
						Color.RED, true);
				isValid = false;
			}
		}
		
		tempString = new OobaFormattingInputFields(context).oobaGetThousandFormatter(Deposit);
		if (tempString.trim().equals("")
				|| tempString.trim()
						.equals(null)) {
			putMessage(Deposit, "Please enter deposit", Color.RED);
			isValid = false;
		}

		return isValid;

	}

	public boolean affordablityCalc(EditText grossMonthlyIncomeEditText,
			EditText netMonthlyEditText, EditText totalMonthlyExpenseEditText,
			TextView netSurplusIncomeEditText, EditText interestRate) {
		boolean isValid = true;
		String tempString = new OobaFormattingInputFields(context).oobaGetThousandFormatter(grossMonthlyIncomeEditText);
		if (tempString.trim().equals("")
				|| tempString.trim()
						.equals(null)) {
			putMessage(grossMonthlyIncomeEditText,
					"Please enter Gross monthly income", Color.RED);
			isValid = false;
		} else {

			long grossMonthlyIncome = Long.parseLong(tempString);
			
			if (grossMonthlyIncome <= 0) {
				putMessage(grossMonthlyIncomeEditText,
						"Must be greater than 0 ", Color.RED, true);
				isValid = false;
			}
		}
		
		tempString = new OobaFormattingInputFields(context).oobaGetThousandFormatter(netMonthlyEditText);
		if (tempString.trim().equals("")
				|| tempString.trim()
						.equals(null)) {
			putMessage(netMonthlyEditText, "Please enter Nett monthly income",
					Color.RED);
			isValid = false;
		} else {
			long netMonthly = Long.parseLong(tempString);
			
			if (netMonthly <= 0) {
				putMessage(netMonthlyEditText, "Must be greater than 0 ",
						Color.RED, true);
				isValid = false;
			}
		}

		tempString = new OobaFormattingInputFields(context).oobaGetThousandFormatter(totalMonthlyExpenseEditText);
		if (tempString.trim().equals("")
				|| tempString.trim()
						.equals(null)) {
			putMessage(totalMonthlyExpenseEditText,
					"Please enter Total monthly expenses", Color.RED);
			isValid = false;
		} else {
			long tme = Long.parseLong(tempString);
			if (tme <= 0) {
				putMessage(totalMonthlyExpenseEditText,
						"Must be greater than 0 ", Color.RED, true);
				isValid = false;
			}
		}

		String temp = interestRate.getText().toString().trim();
		temp = temp.replace("%", "");
		if (temp.equals("")) {
			putMessage(interestRate, "Please enter interest rate", Color.RED);
			isValid = false;
		} else {

			double interestRateDouble = Double.parseDouble(temp);
			if (Double.compare(interestRateDouble, 0.0) < 0 || Double.compare(interestRateDouble, 0.0) == 0) {
				putMessage(interestRate, "Must be greater than 0 ", Color.RED,
						true);
				isValid = false;
			}
		}

		return isValid;
	}

	public boolean homeLoanDepositSaving(EditText propertyvalueEditText,
			EditText depositReqEditText, EditText currentSaving,
			EditText interestRate) {
		boolean isValid = true;
		String tempString = new OobaFormattingInputFields(context).oobaGetThousandFormatter(propertyvalueEditText);
		if (tempString.trim().equals("")
				|| tempString.trim()
						.equals(null)) {
			putMessage(propertyvalueEditText, "Please enter property value",
					Color.RED);
			isValid = false;
		} else {
			long propertyValue = Long.parseLong(tempString);
			if (propertyValue <= 0) {
				putMessage(propertyvalueEditText,
						"Must be greater than 0 ", Color.RED, true);
				isValid = false;
			}
		}

		String temp1 = interestRate.getText().toString().trim();
		temp1 = temp1.replace("%", "");
		if (temp1.equals("")
				|| temp1.equals(null)) {
			putMessage(interestRate, "Please enter Saving interest rate", Color.RED);
			isValid = false;
		} else {
			double interestRateDouble = Double.parseDouble(temp1);
			if (Double.compare(interestRateDouble, 0.0) < 0 ) {
				putMessage(interestRate, "Must be greater than equals to 0 ", Color.RED,
						true);
				isValid = false;
			}
		}

		tempString = new OobaFormattingInputFields(context).oobaGetThousandFormatter(currentSaving);
		if (tempString.trim().equals("")
				|| tempString.trim()
						.equals(null)) {
			putMessage(currentSaving, "Please enter current savings amount",
					Color.RED);			
			isValid = false;
		} else {
			long currentSavingDouble = Long.parseLong(tempString);
			if (currentSavingDouble < 0) {
				putMessage(currentSaving, "Must be greater or equal  0 ", Color.RED,
						true);
				isValid = false;
			}
		}

		String temp = depositReqEditText.getText().toString().trim();
		temp = temp.replace("%", "");
		if (temp.equals("")
				|| temp.equals(null)) {
			putMessage(depositReqEditText, "Please enter Deposit required as %",
					Color.RED);
			isValid = false;
		} else {
			double depositRequired = Double.parseDouble(temp);
			if (Double.compare(depositRequired, 0.0) < 0 || Double.compare(depositRequired, 0.0) == 0) {
				putMessage(depositReqEditText, "Must be greater than 0 ", Color.RED,
						true);
				isValid = false;
			}
		}

		return isValid;
	}

	public boolean bondTransferCostValidation(EditText purchasePrice,
			EditText LoadAmount) {
		boolean isValid = true;
		String tempString = new OobaFormattingInputFields(context).oobaGetThousandFormatter(purchasePrice);
		if (tempString.trim().equals("")
				|| tempString.trim()
						.equals(null)) {
			putMessage(purchasePrice, "Please enter purchase price", Color.RED);
			isValid = false;
		} else {
			long purchasePriceLong = Long.parseLong(tempString);
			if (purchasePriceLong <= 100000) {
				putMessage(purchasePrice, "Must be greater than 100000 ",
						Color.RED, true);
				isValid = false;
			}
		}

		tempString = new OobaFormattingInputFields(context).oobaGetThousandFormatter(LoadAmount);
		if (tempString.trim().equals("")
				|| tempString.trim()
						.equals(null)) {
			putMessage(LoadAmount, "Please enter loan amount", Color.RED);
			isValid = false;
		} else {
			long LoadAmountLong = Long.parseLong(tempString);
			if (LoadAmountLong <= 0) {
				putMessage(LoadAmount, "Must be greater than 0 ", Color.RED,
						true);
				isValid = false;
			}
		}

		return isValid;
	}

	public boolean homeLoanAmmortisationValidation(EditText nomonths,
			EditText interestRate, EditText totalLoanAmount,
			EditText fixedInterestRate) {

		boolean isValid = true;

		if (nomonths.getText().toString().trim().equals("")
				|| nomonths.getText().toString().trim().equals(null)) {
			putMessage(nomonths, "Please enter no of months of loan", Color.RED);
			isValid = false;
		} else {
			long nomonthsLong = Long.parseLong(nomonths.getText().toString().trim());
			if (nomonthsLong < 1) {
				putMessage(nomonths, "Must be greater than 0 ", Color.RED,
						true);
				isValid = false;
			}
		}

		String temp1 = interestRate.getText().toString().trim();
		temp1 = temp1.replace("%", "");
		if (temp1.equals("")
				|| temp1.equals(null)) {
			putMessage(interestRate, "Please enter interest rate", Color.RED);
			isValid = false;
		} else {
			double interestRateDouble = Double.parseDouble(temp1);
			if (Double.compare(interestRateDouble, 0.0) < 0 || Double.compare(interestRateDouble, 0.0) == 0) {
				putMessage(interestRate, "Must be greater than 0 ",
						Color.RED, true);
				isValid = false;
			}
		}

		String tempString = new OobaFormattingInputFields(context).oobaGetThousandFormatter(totalLoanAmount);
		if (tempString.trim().equals("")
				|| tempString.trim()
						.equals(null)) {
			putMessage(totalLoanAmount, "Please enter total loan amount",
					Color.RED);
			isValid = false;
		} else {
			long totalLoanAmountLong = Long.parseLong(tempString);
			if (totalLoanAmountLong <= 0) {
				putMessage(totalLoanAmount, "Must be greater than 0 ",
						Color.RED, true);
				isValid = false;
			}
		}
		
		return isValid;
	}

}
