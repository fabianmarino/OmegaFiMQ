package com.appsolution.layouts;

import com.appsolution.logic.ContactAccount;
import com.appsolution.omegafi.OpenRequestActivity;
import com.appsolution.omegafi.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class DialogContactAccount {

	private AlertDialog.Builder builderDialog;
	private AlertDialog alertDialog;
	private Activity activity;
	private LayoutInflater layoutInflater;
	
	private TextView textNameContact;
	private TextView textCancel;
	private TextView textEmailOpenRequest;
	private TextView callNumberContact;
	
	private boolean isReguarAccount;
	private int idAccount;
	private ContactAccount contact;
	private String phoneNumberExtern;
	private int organizationId;
	
	public DialogContactAccount(Activity activity, boolean isRegular, int idAccount, int organizationId){
		this.activity=activity;
		this.idAccount=idAccount;
		this.isReguarAccount=isRegular;
		this.organizationId=organizationId;
		layoutInflater=(LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.dialog_layout_contact, (ViewGroup)this.activity.findViewById(R.id.layout_root_contact));
		builderDialog = new AlertDialog.Builder(activity);
		builderDialog.setView(layout);	
		alertDialog = builderDialog.create();
		textNameContact=(TextView)layout.findViewById(R.id.titleNameContact);
		textEmailOpenRequest=(TextView)layout.findViewById(R.id.emailOpenRequestContact);
		callNumberContact=(TextView)layout.findViewById(R.id.callNumberContact);
		callNumberContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {	
				callToMember();
				dimissDialog();
			}
		});
		
		if(!isReguarAccount){
			textEmailOpenRequest.setText("Open Request");
			textEmailOpenRequest.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					alertDialog.dismiss();
					if(!contact.isRegularContact()){
						Intent activityOpen=new Intent(DialogContactAccount.this.activity, OpenRequestActivity.class);
						activityOpen.putExtra("id", DialogContactAccount.this.idAccount);
						activityOpen.putExtra("organizationId", DialogContactAccount.this.organizationId);
						DialogContactAccount.this.activity.startActivity(activityOpen);
					}
					else{
						sendEmailToContact();
					}
				}
			});
		}
		
		textCancel=(TextView)layout.findViewById(R.id.cancelDialogContact);
		textCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
				
			}
		});
	}
	
	public void showDialog(){
		alertDialog.show();
	}
	
	public void setNameContact(String name){
		textNameContact.setText(name);
	}
	
	public void setOnOpenRequest(OnClickListener listener){
		textEmailOpenRequest.setOnClickListener(listener);
	}
	
	public void dimissDialog(){
		alertDialog.dismiss();
	}
	
	private void callToMember(){
		Intent intentCall=new Intent(Intent.ACTION_CALL);
		if(contact!=null)
			intentCall.setData(Uri.parse("tel:"+contact.getPhoneToCall()));
		else
			intentCall.setData(Uri.parse("tel:"+phoneNumberExtern));
		
		activity.startActivity(intentCall);
	}
	
	public void setContact(ContactAccount contact){
		this.contact=contact;
		completeDialog();
	}
	
	private void completeDialog(){
		if(this.contact!=null){
			textNameContact.setText(contact.getTitleDialog());
			callNumberContact.setText("Call "+contact.getPhoneContact());
			if(contact.isRegularContact()){
				textEmailOpenRequest.setText("Email "+contact.getEmailContact());
			}
			else{
				textEmailOpenRequest.setText("Open Request");
			}
		}
	}
	
	public void sendEmailToContact(){
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {contact.getEmailContact()});
		Intent mailer = Intent.createChooser(intent, null);
		activity.startActivity(mailer);
	}

	public String getPhoneNumberExtern() {
		return phoneNumberExtern;
	}

	public void setPhoneNumberExtern(String phoneNumberExtern) {
		this.phoneNumberExtern = phoneNumberExtern;
	}
	
	
	
}
