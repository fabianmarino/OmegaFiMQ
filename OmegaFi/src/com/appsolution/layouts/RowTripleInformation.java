package com.appsolution.layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class RowTripleInformation extends RowInformation {

	private TextView textThirdLine;
	
	public RowTripleInformation(Context context){
		super(context);
		this.initialize();
	}
	
	public RowTripleInformation(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
	}
	
	private void initialize(){
		textThirdLine=new TextView(super.getContext());
		textThirdLine.setTextColor(super.getTextNameSubInfo().getTextColors());
		textThirdLine.setTextSize(12);
		textThirdLine.setLayoutParams(super.getTextNameSubInfo().getLayoutParams());
		getLinearNamesInformation().addView(textThirdLine);
	}
	
	public void setThirdLine(String text){
		textThirdLine.setText(text);
	}

}