package com.ooba;



import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;
import nl.matshofman.saxrssreader.RssReader;
import nl.matshofman.saxrssreader.RssHandler;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactUsThankYouActivity {

	Context context;
	String backPressStateString="ContactUs";
	int response;
	
	public ContactUsThankYouActivity(Context context, int response) {
		this.context = context;
		this.response = response;
	}

	public void createLayout() {
		
		
		
		FrameLayout baseLayout = (FrameLayout) ((ContactUsActivity) context)
				.findViewById(R.id.layout_basecontainer);
		LinearLayout inflateLayout = (LinearLayout) ((ContactUsActivity) context)
				.getLayoutInflater().inflate(R.layout.thankyou_contact_layout,
						null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);
		
		((ContactUsActivity)context).getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		
		TextView referenceTextView = (TextView) (baseLayout.findViewById(R.id.textview_thankyou_contactus_layout_reference));
		referenceTextView.setText("Refference number is: "+String.valueOf(response));
		
		Button okButton = (Button) (baseLayout
				.findViewById(R.id.button_thankyou_contact_ok));

		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
							
				((ContactUsActivity)context).createContactUsLayout();
			}
		});

	}

}
