package com.ooba;

import com.ooba.util.FontSettings;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseClass extends Activity {

	Button backButton;
	ImageView image;
	LinearLayout layout;
	LinearLayout infoLayout;
	public LinearLayout backButtonLinearLayout;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_class_activity);
		
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		
		layout = (LinearLayout) findViewById(R.id.linear_layout_topbar);
		infoLayout = (LinearLayout) findViewById(R.id.linear_layout_iicon);
		backButton = (Button) findViewById(R.id.button_baseclass_back);
		backButton.setVisibility(View.INVISIBLE);
		image = (ImageView) findViewById(R.id.imageview_baseclass_i);
		image.setVisibility(View.INVISIBLE);
		infoLayout.setBackgroundResource(R.color.base_top_bar_Background);
		backButtonLinearLayout=(LinearLayout)findViewById(R.id.linear_layout_backbutton);
		backButtonLinearLayout.setBackgroundResource(R.color.base_top_bar_Background);
	
		TextView taglineTextView = (TextView)findViewById(R.id.taglineTextView);
		Typeface tf = new FontSettings(BaseClass.this).getSourceSansProRegular();
		taglineTextView.setTypeface(tf);

	}

}
