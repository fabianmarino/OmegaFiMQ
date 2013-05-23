package com.appsolution.omegafi;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.RowEditInformation;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowToogleOmegaFi;
import com.appsolution.layouts.SectionOmegaFi;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

public class OpenRequestActivity extends OmegaFiActivity {

	private SectionOmegaFi sectionContactInformation;
	private RowEditTextOmegaFi rowEditEmail;
	private RowToogleOmegaFi rowToogle;
	
	private SectionOmegaFi sectionRequestContact;
	private EditText textRequest;
	
	private Button buttonSend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_request);
		sectionContactInformation=(SectionOmegaFi)findViewById(R.id.sectionContactInformation);
		this.completeSectionContactInformation();
		
		sectionRequestContact=(SectionOmegaFi)findViewById(R.id.sectionContactRequest);
		this.completeSectionRequestContact();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Open Request");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completeSectionContactInformation(){
		rowEditEmail=new RowEditTextOmegaFi(this);
		rowEditEmail.setNameInfo("Your Email");
		sectionContactInformation.addView(rowEditEmail);
		
		rowToogle=new RowToogleOmegaFi(this);
		rowToogle.setNameInfo("Previous issue?");
		
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