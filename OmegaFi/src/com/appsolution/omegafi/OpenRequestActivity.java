package com.appsolution.omegafi;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.RowEditInformation;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowToogleOmegaFi;
import com.appsolution.layouts.SectionOmegaFi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class OpenRequestActivity extends OmegaFiActivity {

	private SectionOmegaFi sectionContactInformation;
	private LinearLayout linearTextEdit;
	private TextView textPleaseEnter;
	private EditText editEmailAddres;
	private RowToogleOmegaFi rowToogle;
	
	private SectionOmegaFi sectionRequestContact;
	private EditText textRequest;
	
	private Button buttonSend;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_request);
		sectionContactInformation=(SectionOmegaFi)findViewById(R.id.sectionContactInformation);
		this.completeSectionContactInformation();
		sectionRequestContact=(SectionOmegaFi)findViewById(R.id.sectionContactRequest);
		this.completeSectionRequestContact();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("OPEN REQUEST");
		actionBar.setCustomView(actionBarCustom);
	}
	
	private void completeSectionContactInformation(){
		
		linearTextEdit=new LinearLayout(getApplicationContext());
		linearTextEdit.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		linearTextEdit.setOrientation(LinearLayout.VERTICAL);
		linearTextEdit.setBackgroundResource(R.drawable.border_bottom);
		linearTextEdit.setPadding(10, 10, 10, 10);
		
		textPleaseEnter=new TextView(getApplicationContext());
		textPleaseEnter.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		textPleaseEnter.setText("Please enter the email address associated to your account");
		textPleaseEnter.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		textPleaseEnter.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_15sp));
		textPleaseEnter.setPadding(0, 0, 0, 10);
		textPleaseEnter.setSingleLine(false);
		textPleaseEnter.setTextColor(Color.BLACK);
		
		editEmailAddres=new EditText(getApplicationContext());
		editEmailAddres.setTextColor(Color.BLACK);
		editEmailAddres.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		editEmailAddres.setBackgroundResource(R.drawable.white_input);
		editEmailAddres.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		
		rowToogle=new RowToogleOmegaFi(this);
		rowToogle.setNameInfo("Previous issue?");
		rowToogle.setYesNoToogle(true);
		
		linearTextEdit.addView(textPleaseEnter);
		linearTextEdit.addView(editEmailAddres);
		
		sectionContactInformation.addView(linearTextEdit);
		sectionContactInformation.addView(rowToogle);
	}
	
	private void completeSectionRequestContact(){
		textRequest=new EditText(this);
		textRequest.setGravity(Gravity.TOP);
		textRequest.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		textRequest.setLines(5);
		textRequest.setSingleLine(false);
		
		int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getApplicationContext().getResources().getDisplayMetrics());
		sectionRequestContact.getLinearContent().setPadding(padding,padding, padding, padding);
		sectionRequestContact.addView(textRequest);
	}
	
	public void sendRequestContact(View button){
		final DialogInformationOF information=new DialogInformationOF(this);
		information.setMessageDialog("Thank you for sending in your request. We will get back to you as soon as possible.");
		information.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				information.dismissDialog();
				Intent intent=new Intent(getApplicationContext(), AccountActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		information.showDialog();
	}

}