package com.appsolution.layouts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appsolution.omegafi.R;

public class DialogTwoOptionsOF {

	private AlertDialog.Builder builderDialog;
	private AlertDialog alertDialog;
	private Activity activity;
	private LayoutInflater layoutInflater;
	
	private TextView textMessage;
	private Button buttonOption1;
	private Button buttonOption2;
	
	public DialogTwoOptionsOF(Activity activity){
		this.activity=activity;
		layoutInflater=(LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.dialog_two_options_omegafi,
				(ViewGroup)this.activity.findViewById(R.id.layout_root_two_options));
		builderDialog = new AlertDialog.Builder(activity);
		builderDialog.setView(layout);
		textMessage=(TextView)layout.findViewById(R.id.messageInformationDialog);
		buttonOption1=(Button)layout.findViewById(R.id.buttonOption1);
		buttonOption2=(Button)layout.findViewById(R.id.buttonOption2);
		
		alertDialog = builderDialog.create();
	}
	
	public void setMessageDialog(String message){
		textMessage.setText(message);
	}
	
	public void setOption1(String option){
		buttonOption1.setText(option);
	}
	
	public void setOption2(String option){
		buttonOption2.setText(option);
	}
	
	public void showDialog(){
		alertDialog.show();
	}
	
	public void dismissDialog(){
		alertDialog.dismiss();
	}
	
	public void setListenerOption1(View.OnClickListener event){
		buttonOption1.setOnClickListener(event);
	}
	
	public void setListenerOption2(View.OnClickListener event){
		buttonOption2.setOnClickListener(event);
	}
	
}
