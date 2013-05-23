package com.appsolution.layouts;

import com.appsolution.omegafi.OpenRequestActivity;
import com.appsolution.omegafi.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DialogContactAccount {

	private AlertDialog.Builder builderDialog;
	private AlertDialog alertDialog;
	private Activity activity;
	private LayoutInflater layoutInflater;
	
	private TextView textCancel;
	private TextView textEmailOpenRequest;
	
	private boolean isReguarAccount;
	
	public DialogContactAccount(Activity activity, boolean isRegular){
		this.activity=activity;
		this.isReguarAccount=isRegular;
		layoutInflater=(LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.dialog_layout_contact, (ViewGroup)this.activity.findViewById(R.id.layout_root_contact));
		builderDialog = new AlertDialog.Builder(activity);
		builderDialog.setView(layout);	
		alertDialog = builderDialog.create();
		textEmailOpenRequest=(TextView)layout.findViewById(R.id.emailOpenRequestContact);
		
		if(!isReguarAccount){
			textEmailOpenRequest.setText("Open Request");
			textEmailOpenRequest.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					alertDialog.dismiss();
					Intent activityOpen=new Intent(DialogContactAccount.this.activity, OpenRequestActivity.class);
					DialogContactAccount.this.activity.startActivity(activityOpen);
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
	
}
