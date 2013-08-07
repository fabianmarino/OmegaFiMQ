package com.appsolution.omegafi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.RowEditInformation;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowEditTextSubmit;
import com.appsolution.layouts.RowToogleOmegaFi;
import com.appsolution.layouts.SectionOmegaFi;
import com.appsolution.logic.Server;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
	private int idAccount=-1;
	private int organizationNumber;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_request);
		sectionContactInformation=(SectionOmegaFi)findViewById(R.id.sectionContactInformation);
		this.completeSectionContactInformation();
		sectionRequestContact=(SectionOmegaFi)findViewById(R.id.sectionContactRequest);
		this.completeSectionRequestContact();
		idAccount=getIntent().getExtras().getInt("id");
		organizationNumber=getIntent().getExtras().getInt("organizationId");
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
		int padding=getResources().getDimensionPixelSize(R.dimen.padding_6dp);
		linearTextEdit.setPadding(padding,padding,padding,padding);
		
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
		String validateRequest=this.validateRequest();
		if(validateRequest==null){
			sendRequest();
		}
		else{
			OmegaFiActivity.showAlertMessage(validateRequest, this);
		}
		
	}
	
	private void showRequestSucessfully(){
		final DialogInformationOF information=new DialogInformationOF(this);
		information.setMessageDialog("Thank you for sending in your request. We will get back to you as soon as possible.");
		information.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				information.dismissDialog();
				finish();
			}
		});
		information.showDialog();
	}
	
	private String validateRequest(){
		String validate=null;
		Log.d("validate", editEmailAddres.getText().toString());
		if(!RowEditTextSubmit.isValidEmail(editEmailAddres.getText().toString())){
			validate="Email sintax is invalid.";
		}
		else if(textRequest.getText().toString().isEmpty()){
			validate="The request is empty.";
		}
		return validate;
	}
	
	public int isPreviousIssue(){
		if(rowToogle.isActivatedOn()){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	private void sendRequest(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private int status=0;
			private JSONObject errors=null;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Sending Request...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusJson=Server.getServer().getHome().getAccounts().sendOpenRequest
						(idAccount, "322232", organizationNumber+"", editEmailAddres.getText().toString(),isPreviousIssue(), textRequest.getText().toString());
				status=(Integer)statusJson[0];
				errors=(JSONObject)statusJson[1];
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(status==200||status==201){
					showRequestSucessfully();
				}
				else if(status==422){
					showAlertMessage(error422(errors), OpenRequestActivity.this);
				}
				else{
					OmegaFiActivity.showErrorConection(OpenRequestActivity.this, status, getResources().getString(R.string.object_not_found),
							false);
				}
				stopProgressDialog();
			}
		};
		task.execute();
	}
	
	
	private String error422(JSONObject json){
		String typeError=null;
		JSONArray jsonErrors=null;
		try {
			if(json.has("phonenumber")){
				typeError="Phone Number: ";	
				jsonErrors=json.getJSONArray("phonenumber");
			}
			else if(json.has("openedby")){
				typeError="Opened By: ";	
				jsonErrors=json.getJSONArray("openedby");
			}
			else if(json.has("organizationnumber")){
				typeError="Organization Number: ";	
				jsonErrors=json.getJSONArray("organizationnumber");
			}
			else if(json.has("email")){
				typeError="Email: ";	
				jsonErrors=json.getJSONArray("email");
			}
			else if(json.has("request")){
				typeError="Request: ";	
				jsonErrors=json.getJSONArray("request");
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String completeError=null;
		try {
			if(typeError!=null){
				completeError=typeError+jsonErrors.getString(0);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return completeError;
		
	}

}