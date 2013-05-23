package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;

public class RowAnnouncement extends RelativeLayout {

	private Button buttonPayNow;
	
	public RowAnnouncement(Context context){
		super(context);
		this.initialize();
	}
	
	public RowAnnouncement(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
	}
	
	private void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.row_announcemen, this, true);
		buttonPayNow=(Button)findViewById(R.id.buttonPayNow);
	}
	
	public void setButtonPayNowListener(OnClickListener listener){
		buttonPayNow.setOnClickListener(listener);
	}
}