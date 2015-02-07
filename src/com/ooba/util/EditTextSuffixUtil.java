package com.ooba.util;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class EditTextSuffixUtil {

	Context context;
	boolean isChanged = false;
	
	public EditTextSuffixUtil(Context context){
		this.context = context;
	}
	
	public void onEditTextFocusListener(final EditText editText){
		editText
		.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String tempString = editText.getText()
						.toString().trim();
				if (hasFocus && !isChanged) {
					tempString = tempString.replace("%", "");
				} else {
					tempString += "%";
				}
				editText.setText(tempString);
			}
		});
	}
	
	public void onEditTextEditor(final EditText editText){
		editText
		.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					String tempString = editText.getText()
							.toString().trim();
					tempString += "%";
					editText.setText(tempString);
					isChanged = true;
				}
				return false;
			}
		});
	}
}
