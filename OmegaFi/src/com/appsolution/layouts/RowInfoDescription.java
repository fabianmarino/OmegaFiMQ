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
	private TextView textTitle;
	private TextView textRight;
	private TextView textSource;
	
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
		textTitle=(TextView)findViewById(R.id.textTitleCalendar);
		textRight=(TextView)findViewById(R.id.textRightContent);
		textSource=(TextView)findViewById(R.id.textSourceCalendar);
	}
	
	public void setLeftText(String text){
		textLeft.setText(text);
	}
	
	public void setRightText(String text){
		textRight.setText(text);
	}
	
	public void setTitleText(String text){
		textTitle.setText(text);
	}
	
	public void setInfoDescription(String title,String textLeft, String textRight){
		setTitleText(title);
		setLeftText(textLeft);
		setRightText(textRight);
	}
	
	public void setSourceEventCalendar(String source){
		if(source!=null)
			textSource.setText("Source: "+source);
		else
			textSource.setText("Source: ");
	}
	
}