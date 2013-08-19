package com.appsolution.layouts;

import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LayoutActionBar extends RelativeLayout {

	private TextView titleBar;
	
	private LinearLayout linearArrow;
	
	public LayoutActionBar(Context context) {
		super(context);
		this.initialize();
	}

	public LayoutActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
	}
	
	
	private void initialize() {
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.layout_action_bar, this, true);
		titleBar=(TextView)findViewById(R.id.titleActionBar);
		titleBar.setTypeface(OmegaFiActivity.getFont(getContext(), 1));
	
		linearArrow=(LinearLayout)findViewById(R.id.linearArrowBack);
	}
	
	public void setTitle(String title){
		titleBar.setText(title);
	}
	
	public void setArrowClickListener(View.OnClickListener l){
		linearArrow.setOnClickListener(l);
	}
}
