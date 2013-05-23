package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RowInfoDescription extends LinearLayout {

	private TextView textLeft;
	private TextView textRight;
	
	public RowInfoDescription(Context context){
		super(context);
		this.initialize();
	}
	
	public RowInfoDescription(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RowInfoDescription);
		
		String textLeft = a.getString(R.styleable.RowInfoDescription_text_left_info);
		setLeftText(textLeft);
		
		String textRight = a.getString(R.styleable.RowInfoDescription_text_right_info);
		setRightText(textRight);
		
		a.recycle();
	}
	
	private void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.row_info_description, this, true);
		textLeft=(TextView)findViewById(R.id.textLeftContent);
		textRight=(TextView)findViewById(R.id.textRightContent);
	}
	
	public void setLeftText(String text){
		textLeft.setText(text);
	}
	
	public void setRightText(String text){
		textRight.setText(text);
	}
	
	public void setInfoDescription(String textLeft, String textRight){
		setLeftText(textLeft);
		setRightText(textRight);
	}
	
}