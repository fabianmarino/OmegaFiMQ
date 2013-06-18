package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

public class DetailsOfficer extends LinearLayout {

	
	public DetailsOfficer(Context context){
		super(context);
		this.initializate();
	}
	
	public DetailsOfficer(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initializate();
	}
	
	
	private void initializate(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.details_officer, this, true);
	}
	

}
