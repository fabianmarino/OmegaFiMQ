package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AccountNotification extends LinearLayout {
	
//	private LinearLayout contentNotification;
	private TextView textNotification;

	public AccountNotification(Context context) {
		super(context);
		this.initialize();
	}

	public AccountNotification(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
	}
	
	private void initialize() {
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.account_notification, this, true);
//		contentNotification=(LinearLayout)findViewById(R.id.contentNotification);
		textNotification=(TextView)findViewById(R.id.textNotificationAccount);
	}
	
	
	public void setTextNotification(String text){
		textNotification.setText(text);
	}

}
