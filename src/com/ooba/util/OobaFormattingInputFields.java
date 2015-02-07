package com.ooba.util;

import android.R.bool;
import android.content.Context;
import android.os.Handler;
import android.test.PerformanceTestCase;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class OobaFormattingInputFields {

	Context context;
	View view;
	
	public OobaFormattingInputFields(Context context) {
		this.context = context;		
	}
	public OobaFormattingInputFields(Context context, View v) {
		this.context = context;
		this.view = v;		
	}
	
	public String stringThousandFormatting(String string){		
		string = string.replace(",", " ");
		string = string.replace(".00", "");
		return "R " + string;
		
	}

	public void oobaThousandFormatter(final EditText text) {
		try {

			text.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					EditText t = (EditText) v;
					if (hasFocus == true) {
						if (t.getText().toString().contains("R") == true) {

							String s = t.getText().toString().trim();
							s = s.replace("R", "");
							s = s.replace(" ", "");
							s = s.replace(".00", "");
							s = s.trim();
							t.setText(s);
							t.setSelection(t.getText().length());
						}
					} else {
						try {
							String num = t.getText().toString().trim();
							double d = Double.parseDouble(num);
							num = DoubleFormaterClass.priceWithDecimal(d);
							num = num.replace(",", " ");
							num = num.replace(".00", "");
							String n = "R " + num;
							if(n.equals("R ")){
								;
								n = "R 0";
							}
							t.setText(n);
						} catch (Exception e) {

							e.printStackTrace();
						}

					}

				}
			});

		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>>" + e.getMessage());
		}
	}
	
	public void oobaThousandFormatterNextClick(final EditText text) {
		try {

			text.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					EditText t = (EditText) v;
					if (hasFocus == true) {
						if (t.getText().toString().contains("R") == true) {

							String s = t.getText().toString().trim();
							s = s.replace("R", "");
							s = s.replace(" ", "");
							s = s.replace(".00", "");
							s = s.trim();
							t.setText(s);
							t.setSelection(t.getText().length());
						}
					} else {
						try {
							String num = t.getText().toString().trim();
							double d = Double.parseDouble(num);
							num = DoubleFormaterClass.priceWithDecimal(d);
							num = num.replace(",", " ");
							num = num.replace(".00", "");
							String n = "R " + num;
							t.setText(n);
						} catch (Exception e) {

							e.printStackTrace();
						}

					}

				}
			});

			text.setOnEditorActionListener(new OnEditorActionListener() {

				@Override
				public boolean onEditorAction(TextView v, int actionId,
						KeyEvent event) {
					if (actionId == EditorInfo.IME_ACTION_GO
							|| actionId == EditorInfo.IME_ACTION_DONE 
							|| actionId == EditorInfo.IME_ACTION_NEXT) {
						try {
							String num = text.getText().toString().trim();
							double d = Double.parseDouble(num);
							num = DoubleFormaterClass.priceWithDecimal(d);
							num = num.replace(",", " ");
							num = num.replace(".00", "");
							String n = "R" + num;
							text.setText(n);
						} catch (Exception e) {
							e.printStackTrace();
						}

						InputMethodManager inputManager = (InputMethodManager) context
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						inputManager.hideSoftInputFromWindow(
								text.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
						
						if(view instanceof Button){
							view.performClick();
						}else{
							view.setFocusableInTouchMode(true);
							view.requestFocus();
						}
						
						view.setFocusableInTouchMode(false);
						view.setFocusable(false);
						view.setFocusableInTouchMode(true);
						view.setFocusable(true);
					}

					return true;
				}
			});
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>>" + e.getMessage());
		}
	}
	

	public String oobaGetThousandFormatter(final EditText text) {

		String s = text.getText().toString().trim();
		if (s.contains("R") == true) {

			s = s.replace("R", "");
			s = s.replace(" ", "");
			s = s.replace(".00", "");
			s = s.trim();
		}
		System.out.println(">>>s>>>"+s);
		return s;
	}

	public void oobaInterestRateFormatter(final EditText text) {
		try {

			text.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					EditText t = (EditText) v;
					if (hasFocus == true) {
						if (t.getText().toString().contains("%") == true) {
							String s = t.getText().toString().trim();
							s = s.replace("%", "");
							s = s.trim();
							t.setText(s);
							t.setSelection(t.getText().length());
							(new Handler()).postDelayed(new Runnable() {

								public void run() {
									InputMethodManager imm = (InputMethodManager) context
											.getSystemService(Context.INPUT_METHOD_SERVICE);
									imm.showSoftInput(text,
											InputMethodManager.SHOW_IMPLICIT);
								}
							}, 200);
						}else {

							(new Handler()).postDelayed(new Runnable() {

								public void run() {
									InputMethodManager imm = (InputMethodManager) context
											.getSystemService(Context.INPUT_METHOD_SERVICE);
									imm.showSoftInput(text,
											InputMethodManager.SHOW_IMPLICIT);
								}
							}, 200);

						}
					} else {
						try {
							String num = t.getText().toString().trim();
							double d = Double.parseDouble(num);
							String n = num + "%";
							t.setText(n);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}
			});

			text.setOnEditorActionListener(new OnEditorActionListener() {

				@Override
				public boolean onEditorAction(TextView v, int actionId,
						KeyEvent event) {
					if (actionId == EditorInfo.IME_ACTION_GO
							|| actionId == EditorInfo.IME_ACTION_DONE 
							|| actionId == EditorInfo.IME_ACTION_NEXT) {
						try {
							String num = text.getText().toString().trim();
							double d = Double.parseDouble(num);
							String n = num + "%";
							text.setText(n);
						} catch (Exception e) {
							e.printStackTrace();
						}
						InputMethodManager inputManager = (InputMethodManager) context
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						inputManager.hideSoftInputFromWindow(
								text.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);

						text.setFocusableInTouchMode(false);
						text.setFocusable(false);
						text.setFocusableInTouchMode(true);
						text.setFocusable(true);
						if(view instanceof Button){
							view.performClick();
						}else{
							view.setFocusableInTouchMode(true);
							view.requestFocus();
						}
					}

					return true;
				}
			});

		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>>" + e.getMessage());
		}
	}

	
	public void oobaInterestRateFormatterNotLast(final EditText text) {
		try {

			text.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					EditText t = (EditText) v;
					if (hasFocus == true) {
						if (t.getText().toString().contains("%") == true) {
							String s = t.getText().toString().trim();
							s = s.replace("%", "");
							s = s.trim();
							t.setText(s);
							t.setSelection(t.getText().length());
							InputMethodManager inputManager = (InputMethodManager) context
									.getSystemService(Context.INPUT_METHOD_SERVICE);
							inputManager.hideSoftInputFromWindow(
									text.getWindowToken(),
									InputMethodManager.SHOW_IMPLICIT);
						}
					} else {
						try {
							String num = t.getText().toString().trim();
							double d = Double.parseDouble(num);
							String n = num + "%";
							t.setText(n);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}
			});


		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>>" + e.getMessage());
		}
	}
	
	public void oobaInterestRateFormatterShowKeyboard(final EditText text) {
		try {

			text.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					EditText t = (EditText) v;
					if (hasFocus == true) {
						if (t.getText().toString().contains("%") == true) {
							String s = t.getText().toString().trim();
							s = s.replace("%", "");
							s = s.trim();
							t.setText(s);
							t.setSelection(t.getText().length());
							(new Handler()).postDelayed(new Runnable() {

								public void run() {
									InputMethodManager imm = (InputMethodManager) context
											.getSystemService(Context.INPUT_METHOD_SERVICE);
									imm.showSoftInput(text,
											InputMethodManager.SHOW_IMPLICIT);
								}
							}, 200);
						}
					} else {
						try {
							String num = t.getText().toString().trim();
							double d = Double.parseDouble(num);
							String n = num + "%";
							t.setText(n);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}
			});

			text.setOnEditorActionListener(new OnEditorActionListener() {

				@Override
				public boolean onEditorAction(TextView v, int actionId,
						KeyEvent event) {
					if (actionId == EditorInfo.IME_ACTION_GO
							|| actionId == EditorInfo.IME_ACTION_DONE
							|| actionId == EditorInfo.IME_ACTION_NEXT) {
						try {
							String num = text.getText().toString().trim();
							String n = num + "%";
							text.setText(n);
						} catch (Exception e) {
							e.printStackTrace();
						}

						InputMethodManager inputManager = (InputMethodManager) context
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						inputManager.hideSoftInputFromWindow(
								text.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
						
						if(view instanceof Button){
							view.performClick();
						}else{
							view.setFocusableInTouchMode(true);
							view.requestFocus();
						}
					}

					return true;
				}
			});

		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>>" + e.getMessage());
		}
	}
	
}
