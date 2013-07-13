package com.appsolution.layouts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appsolution.omegafi.R;

public class DialogInformationOF {
	private AlertDialog.Builder builderDialog;
	private AlertDialog alertDialog;
	private Activity activity;
	private LayoutInflater layoutInflater;
	
	private TextView textMessage;
	private Button buttonOK;
	
	public DialogInformationOF(Activity activity){
		this.activity=activity;
		layoutInflater=(LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.dialog_information_omegafi,
				(ViewGroup)this.activity.findViewById(R.id.layout_root_info_dialog));
		builderDialog = new AlertDialog.Builder(activity);
		builderDialog.setView(layout);
		
		textMessage=(TextView)layout.findViewById(R.id.messageInformationDialog);
		buttonOK=(Button)layout.findViewById(R.id.buttonOKInformation);
		
		alertDialog = builderDialog.create();
	}
	
	public void setMessageDialog(String message){
		textMessage.setText(message);
	}
	
	public void showDialog(){
		alertDialog.show();
	}
	
	public void dismissDialog(){
		alertDialog.dismiss();
	}
	
	public void setButtonListener(View.OnClickListener event){
		buttonOK.setOnClickListener(event);
	}
	
	public static OnClickListener getDismissListener(final DialogInformationOF of){
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				of.dismissDialog();	
			}
		};
	}
}