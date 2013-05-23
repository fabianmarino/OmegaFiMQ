package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TableLayout;

public class SectionAccountInfo extends TableLayout {

	public SectionAccountInfo(Context context){
		super(context);
		this.inflateLayout();
	}
	
	public SectionAccountInfo(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.inflateLayout();
	}
	
	private void inflateLayout(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.section_account_user_details, this, true);
	}

}