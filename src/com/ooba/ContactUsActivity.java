package com.ooba;

import java.util.ArrayList;

import com.google.analytics.tracking.android.EasyTracker;
import com.ooba.Adapter.NatureOfEnquiryBaseAdapter;
import com.ooba.entity.NatureOfEnquiry;
import com.ooba.entity.WebsiteLeadDTO;
import com.ooba.services.OobaWebServices;
import com.ooba.util.BackgroundNetwork;
import com.ooba.util.FontSettings;
import com.ooba.util.OobaGoogleAnalytics;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ContactUsActivity extends BaseClass {

	Button sendButton;
	TextView natureOfEnquiryTextView;
	EditText firstNameEditText;
	EditText lastNameEditText;
	EditText contactEditText;
	EditText emailEditText;
	EditText messagesEditText;

	String backPressStateString = "";
	int response = 0;

	boolean isBroadcast = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			new OobaGoogleAnalytics(ContactUsActivity.this).sendTrackedEvent(
					"ContatctUs_Page",
					"ContactUs_Activity", "0");
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}
		createContactUsLayout();
	}

	@Override
	public void onBackPressed() {
		if (backPressStateString.equals("ContactUs")) {
			backPressStateString = "";
			createContactUsLayout();
		} else {
			finish();
		}
	}

	public void createContactUsLayout() {

		FrameLayout framelayout = (FrameLayout) findViewById(R.id.layout_basecontainer);
		framelayout.removeAllViews();
		framelayout.addView(getLayoutInflater().inflate(
				R.layout.contact_us_activity, null));

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		backButton.setVisibility(View.INVISIBLE);

		sendButton = (Button) findViewById(R.id.button_contactus_send);

		natureOfEnquiryTextView = (TextView) findViewById(R.id.textview_contactus_enquiry);
		firstNameEditText = (EditText) findViewById(R.id.edittext_contactus_firstname);
		lastNameEditText = (EditText) findViewById(R.id.edittext_contactus_lastname);
		contactEditText = (EditText) findViewById(R.id.edittext_contactus_contactnumber);
		emailEditText = (EditText) findViewById(R.id.edittext_contactus_email);
		messagesEditText = (EditText) findViewById(R.id.edittext_contactus_comments);

		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String dataString = intent.getStringExtra("natureOfEnquiry");
				natureOfEnquiryTextView.setText(dataString);
			}
		};

		IntentFilter filter = new IntentFilter();
		filter.addAction("com.ooba.ContactUsActivity");
		registerReceiver(receiver, filter);

		TextView textview_contactus_label_contactus = (TextView) findViewById(R.id.textview_contactus_label_contactus);
		TextView textview_contactus_label_tagline = (TextView) findViewById(R.id.textview_contactus_label_tagline);
		TextView textview_contactus_label_firstname = (TextView) findViewById(R.id.textview_contactus_label_firstname);
		TextView textview_contactus_label_lastname = (TextView) findViewById(R.id.textview_contactus_label_lastname);
		TextView textview_contactus_label_contactnumber = (TextView) findViewById(R.id.textview_contactus_label_contactnumber);
		TextView textview_contactus_label_emailaddress = (TextView) findViewById(R.id.textview_contactus_label_emailaddress);
		TextView textview_contactus_label_natureofenquiry = (TextView) findViewById(R.id.textview_contactus_label_natureofenquiry);
		TextView textview_contactus_label_message = (TextView) findViewById(R.id.textview_contactus_label_message);

		Typeface tf = new FontSettings(ContactUsActivity.this)
				.getSourceSansProRegular();

		textview_contactus_label_contactus.setTypeface(tf);
		textview_contactus_label_tagline.setTypeface(tf);
		textview_contactus_label_firstname.setTypeface(tf);
		textview_contactus_label_lastname.setTypeface(tf);
		textview_contactus_label_contactnumber.setTypeface(tf);
		textview_contactus_label_emailaddress.setTypeface(tf);
		textview_contactus_label_natureofenquiry.setTypeface(tf);
		textview_contactus_label_message.setTypeface(tf);

		natureOfEnquiryTextView.setTypeface(tf);
		firstNameEditText.setTypeface(tf);
		lastNameEditText.setTypeface(tf);
		contactEditText.setTypeface(tf);
		emailEditText.setTypeface(tf);
		messagesEditText.setTypeface(tf);

		emailEditText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				if (actionId == EditorInfo.IME_ACTION_NEXT) {
					showNatureEnquiryList();
				}

				return false;
			}
		});

		natureOfEnquiryTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showNatureEnquiryList();
			}
		});

		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				backPressStateString = "ContactUs";
				Boolean isDataFilled = true;

				if (firstNameEditText.getText().toString().trim().equals("")) {
					isDataFilled = false;
					firstNameEditText
							.setHint("Please fill out the required fields");
				}

				if (contactEditText.getText().toString().trim().equals("")) {
					isDataFilled = false;
					contactEditText
							.setHint("Please fill out the required fields");
				}

				if (emailEditText.getText().toString().trim().equals("")) {
					isDataFilled = false;
					emailEditText
							.setHint("Please fill out the required fields");
				}

				if (natureOfEnquiryTextView.getText().toString().trim()
						.equals("")) {
					isDataFilled = false;
					natureOfEnquiryTextView
							.setHint("Please select one of the option");
				}

				if (messagesEditText.getText().toString().trim().equals("")) {
					isDataFilled = false;
					messagesEditText
							.setHint("Please fill out the required fields");
				} else {

			         String emailPattern =
			                 "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
			                     +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
			                       +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
			                       +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
			                       +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
			                       +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

					String tempEmail = emailEditText.getText().toString()
							.trim();
					if (!tempEmail.matches(emailPattern)) {
						isDataFilled = false;
						emailEditText.setText("");
						emailEditText
								.setHint("Please enter a valid email address");
					}
				}

				if (isDataFilled == false) {
					return;
				}

				final WebsiteLeadDTO websiteLeadDTO = new WebsiteLeadDTO();
				websiteLeadDTO.firstName = firstNameEditText.getText()
						.toString().trim();
				if (lastNameEditText.getText().toString().trim().equals("")) {
					websiteLeadDTO.lastName = "";
				} else {
					websiteLeadDTO.lastName = lastNameEditText.getText()
							.toString().trim();
				}
				websiteLeadDTO.cellPhone = contactEditText.getText().toString()
						.trim();
				websiteLeadDTO.email = emailEditText.getText().toString()
						.trim();
				websiteLeadDTO.comments = messagesEditText.getText().toString()
						.trim();
				websiteLeadDTO.natureOfEnquiry = natureOfEnquiryTextView
						.getText().toString().trim();

				new BackgroundNetwork(ContactUsActivity.this) {

					protected Void doInBackground(Void[] params) {

						response = new OobaWebServices()
								.saveContactDetails(websiteLeadDTO);

						return null;
					};

					protected void onPostExecute(Void result) {
						if (response != 0) {
							backPressStateString = "ContactUs";
							try {
								new OobaGoogleAnalytics(ContactUsActivity.this).sendTrackedEvent("Button Click",
										"Contact Us", "Send " + natureOfEnquiryTextView.getText().toString() + "Message" );
							} catch (Exception e) {
								System.out.println(">>>Exception>>>" + e.toString()
										+ ">>>Message>>" + e.getMessage());
							}
							
							new ContactUsThankYouActivity(
									ContactUsActivity.this, response)
									.createLayout();
						} else {
							showMessage("Unable to send the contact details.");
						}
						super.onPostExecute(result);
					};
				}.execute();

			}
		});

		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				backPressStateString = "";
				backButton.setVisibility(View.INVISIBLE);
				createContactUsLayout();
			}
		});
	}

	@Override
	protected void onResume() {
		try {
			new OobaGoogleAnalytics(ContactUsActivity.this).sendTrackedEvent("Contact Us Page",
					"Contact Us", "Contact Us");
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>" + e.getMessage());
		}
		
		firstNameEditText.setText("");
		firstNameEditText.setHint("");
		lastNameEditText.setText("");
		lastNameEditText.setHint("");
		contactEditText.setText("");
		contactEditText.setHint("");
		emailEditText.setText("");
		emailEditText.setHint("");
		if (!isBroadcast) {
			natureOfEnquiryTextView.setText("Prequalify Now");
		}
		messagesEditText.setText("");
		messagesEditText.setHint("");
		super.onResume();
	}

	public void showNatureEnquiryList() {
		final Dialog dialog1 = new Dialog(ContactUsActivity.this);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog1.setContentView(R.layout.nature_of_query_layout);

		ListView stringListView = (ListView) dialog1
				.findViewById(R.id.listview_nature_of_enquiry_listiview);
		final ArrayList<NatureOfEnquiry> stringList = new ArrayList<NatureOfEnquiry>();
		stringList.add(new NatureOfEnquiry("Prequalify Now"));
		stringList.add(new NatureOfEnquiry("Bond Enquiry"));
		stringList.add(new NatureOfEnquiry("Insurance Enquiry"));
		stringList.add(new NatureOfEnquiry("General"));

		NatureOfEnquiryBaseAdapter adapter = new NatureOfEnquiryBaseAdapter(
				ContactUsActivity.this, stringList);
		stringListView.setAdapter(adapter);

		stringListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				natureOfEnquiryTextView.setText(stringList.get(position).listString);
				messagesEditText.setFocusableInTouchMode(true);
				messagesEditText.requestFocus();
				dialog1.dismiss();				
			}

		});

		dialog1.show();

		dialog1.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				messagesEditText.post(new Runnable() {
					public void run() {
						messagesEditText.requestFocusFromTouch();
						InputMethodManager lManager = (InputMethodManager) ContactUsActivity.this
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						lManager.showSoftInput(messagesEditText, 0);
					}
				});

			}
		});

	}

	public void showMessage(String message) {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(
				ContactUsActivity.this);
		dlgAlert.setTitle("Error");
		dlgAlert.setMessage(message);
		dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}
 
	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}
}
