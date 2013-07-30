package com.appsolution.layouts;

import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotificationLayout extends LinearLayout{

	private TextView textNotification;
	
	public NotificationLayout(Context context) {
		super(context);
		this.initialize();
	}
	
	public NotificationLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
	}

	private void initialize() {
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.notification_layout, this, true);
		textNotification=(TextView)findViewById(R.id.textNotification);
		textNotification.setTypeface(OmegaFiActivity.getFont(getContext(), 0));
	}
	
	public void setNotification(String notification){
		textNotification.setText(notification);
	}

}
